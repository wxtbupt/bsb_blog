package com.bisaibang.monojwt.service.team;

import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.team.TeamPlayer;
import com.bisaibang.monojwt.domain.vm.TeamCreateVM;
import com.bisaibang.monojwt.domain.vm.TeamInfoVM;
import com.bisaibang.monojwt.domain.vm.TeamPlayerVM;
import com.bisaibang.monojwt.repository.RegistrationRepository;
import com.bisaibang.monojwt.repository.TeamPlayerRepository;
import com.bisaibang.monojwt.repository.TeamRepository;
import com.bisaibang.monojwt.repository.UserRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.web.rest.generate.util.PaginationUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;
import static java.util.stream.Collectors.toList;

/**
 * Created by liujinduo on 2016/10/26.
 */
@Service
public class MSTeamService {
    private final Logger log = LoggerFactory.getLogger(MSTeamService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private RegistrationRepository registrationRepository;

    public ResponseEntity<?> createTeam(TeamCreateVM tCVM) {
        TeamCreateVM data = msAuthority.TCVMSQLInject(tCVM);
        return teamRepository.findOneByName(tCVM.getName())
            .map(team -> ResponseEntity.ok(message("小队名已存在")))
            .orElseGet(() -> {
                Team t = Team.create(data, msAuthority.current());
                teamRepository.save(t);
                TeamPlayer tP = TeamPlayer.createCaptain(t, msAuthority.current());
                teamPlayerRepository.save(tP);
                data.getTeamPlayer()
                    .forEach(teamPlayer -> {
                        teamPlayerRepository.save(TeamPlayer.create(t, teamPlayer));
                    });
                return ResponseEntity.ok(message("创建小队成功"));
            });
    }

    public ResponseEntity<?> getAllPlayer(Long teamid) {
        return ResponseEntity.ok(teamPlayerRepository.findAllByTeam(Team.create(teamid)));
    }

   /* public ResponseEntity<?> updateTeam(Long teamid, TeamInfoVM teamInfoVM) {
        Team t = teamRepository.findOne(teamid);
        t.setName(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getName()));
        t.setDescription(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getDescription()));
        t.setTeamAvatar(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getTeamAvatar()));
        t.setJoinState(teamInfoVM.getTeam().getJoinState());
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findAllByTeam(t);
        for (int i = 0; i < teamInfoVM.getTeamPlayer().size() - 1; i++) {
            if (i == 0) {
                teamPlayers.get(0).getTeam().getCaptain().setNickName(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getCaptain().getNickName()));
                teamPlayers.get(0).getTeam().getCaptain().setPersonalID(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getCaptain().getPersonalID()));
                teamPlayers.get(0).getTeam().getCaptain().setPhone(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getCaptain().getPhone()));
                teamPlayers.get(0).getTeam().getCaptain().setPersonalEmail(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getCaptain().getPersonalEmail()));
                teamPlayers.get(0).getTeam().getCaptain().setFirstName(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getCaptain().getFirstName()));
            }
            teamPlayers.get(i + 1).setNickName(StringEscapeUtils.escapeSql(teamInfoVM.getTeamPlayer().get(i).getNickName()));
            teamPlayers.get(i + 1).setPersonalId(StringEscapeUtils.escapeSql(teamInfoVM.getTeamPlayer().get(i).getPersonalId()));
            teamPlayers.get(i + 1).setPhone(StringEscapeUtils.escapeSql(teamInfoVM.getTeamPlayer().get(i).getPhone()));
            teamPlayers.get(i + 1).setMail(StringEscapeUtils.escapeSql(teamInfoVM.getTeamPlayer().get(i).getMail()));
            teamPlayers.get(i + 1).setName(StringEscapeUtils.escapeSql(teamInfoVM.getTeamPlayer().get(i).getName()));
            log.debug(teamPlayers.get(i).getName());
        }
        teamPlayerRepository.save(teamPlayers);
        teamRepository.save(t);
        return ResponseEntity.ok(message("修改信息成功"));
    }*/

 public ResponseEntity<?> updateTeam(Long teamid, TeamInfoVM teamInfoVM) {
        Team t = teamRepository.findOne(teamid);
        t.setName(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getName()));
        t.setDescription(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getDescription()));
        t.setTeamAvatar(StringEscapeUtils.escapeSql(teamInfoVM.getTeam().getTeamAvatar()));
        t.setJoinState(teamInfoVM.getTeam().getJoinState());
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findAllByTeamAndState(t, "member");
        teamPlayerRepository.delete(teamPlayers);
        teamInfoVM.getTeamPlayer()
         .forEach(teamPlayer -> {
                 teamPlayerRepository.save(TeamPlayer.create(t, teamPlayer));
        });
        teamRepository.save(t);
        return ResponseEntity.ok(message("修改信息成功"));
    }

    public ResponseEntity<?> getTeamsAll(Pageable pageable) throws URISyntaxException {
        Page<Team> page = teamRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
            "/api//ms-team/teams-all");
        return ResponseEntity.ok(page.getContent());
    }

    public ResponseEntity<?> deleteTeam(Long teamid) {
        Team t = teamRepository.findOne(teamid);
        return registrationRepository.findOneByTeam(t)
            .map(registration -> {
                registrationRepository.delete(registration);
                List<TeamPlayer> teamPlayers = teamPlayerRepository.findAllByTeam(t);
                teamPlayers.stream()
                    .map(teamPlayer -> {
                        teamPlayerRepository.delete(teamPlayer);
                        return teamPlayer;
                    })
                    .collect(toList());
                teamRepository.delete(t);
                return ResponseEntity.ok(message("小队已解散"));
            })
            .orElseGet(() -> {
                List<TeamPlayer> teamPlayers = teamPlayerRepository.findAllByTeam(t);
                teamPlayers.stream()
                    .map(teamPlayer -> {
                        teamPlayerRepository.delete(teamPlayer);
                        return teamPlayer;
                    })
                    .collect(toList());
                teamRepository.delete(t);
                return ResponseEntity.ok(message("小队已解散"));
            });
    }

    public ResponseEntity<?> changeTeamNameByAdmin(Long teamid, String teamName) throws URISyntaxException {
        Team team = teamRepository.findOne(teamid);
        return teamRepository.findOneByName(teamName)
            .map(team1 -> ResponseEntity.ok(message("队伍名已存在")))
            .orElseGet(() -> {
                team.setName(teamName);
                teamRepository.save(team);
                registrationRepository.findOneByTeam(team)
                    .map(registration -> {
                        registration.setTeamName(teamName);
                        registrationRepository.save(registration);
                        return "";
                    })
                    .orElseGet(() -> {
                        return "";
                    });
                return ResponseEntity.ok(message("小队名修改成功"));
            });
    }


    public ResponseEntity<?> addTeamPlayer(TeamPlayerVM teamPlayerVM) {
        return teamRepository.findOneByCaptain(msAuthority.current())
            .map(team -> {
                TeamPlayerVM data = msAuthority.TPVMSQLInject(teamPlayerVM);
                teamPlayerRepository.save(TeamPlayer.create(team, data));
                return ResponseEntity.ok(message("添加替补队员成功"));
            })
            .orElseGet(()->ResponseEntity.ok(message("当前用户不是队长")));

    }

}
