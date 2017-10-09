package com.bisaibang.monojwt.web.rest.team;


import com.bisaibang.monojwt.domain.forum.Forum;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.team.TeamPlayer;
import com.bisaibang.monojwt.domain.vm.PlayerStateChangeVM;
import com.bisaibang.monojwt.domain.vm.TeamCreateVM;
import com.bisaibang.monojwt.domain.vm.TeamInfoVM;
import com.bisaibang.monojwt.domain.vm.TeamPlayerVM;
import com.bisaibang.monojwt.repository.RegistrationRepository;
import com.bisaibang.monojwt.repository.TeamPlayerRepository;
import com.bisaibang.monojwt.repository.TeamRepository;
import com.bisaibang.monojwt.repository.UserRepository;

import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.team.MSTeamService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;
import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class MSTeamResource {


    private final Logger log = LoggerFactory.getLogger(MSTeamResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MSTeamService msTeamService;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    /**
     * 创建小队
     * by userid
     */
    @RequestMapping(value = "/ms-team/create",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createTeam(@Valid @RequestBody TeamCreateVM tCVM) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("请登录"));
        }
        return msTeamService.createTeam(tCVM);
    }


    /**
     * 更新team
     * by captain
     */
    @RequestMapping(value = "/ms-team/captain/update/{teamid}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateTeamByCaptain(@PathVariable Long teamid, @Valid @RequestBody TeamInfoVM teamInfoVM) throws URISyntaxException {
        if (!msAuthority.isCaptain(teamid)) {
            return ResponseEntity.ok(message("越权"));
        }
        return msTeamService.updateTeam(teamid, teamInfoVM);
    }




    /**
     * 获取所有team
     */
    @RequestMapping(value = "/ms-team/teams-all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getTeamsAll(@Valid Pageable pageable) throws URISyntaxException {
        return msTeamService.getTeamsAll(pageable);
    }

    /**
     * 解散小队
     */
    @PostMapping("/ms-team/delete-team/team/{teamid}")
    @Timed
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamid) throws URISyntaxException {
        if (!msAuthority.isTeamManager(teamid)) {
            return ResponseEntity.ok(message("越权"));
        }
        return msTeamService.deleteTeam(teamid);
    }



    /**
     * change the teamName by Admin
     */
    @PostMapping("/ms-team/change/team-name/team/{teamid}")
    @Timed
    public ResponseEntity<?> changeTeamNameByAdmin(@PathVariable Long teamid, @RequestBody String teamName) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return msTeamService.changeTeamNameByAdmin(teamid, teamName);
    }

    /**
     * 判断是否报名团队赛(是否创建过小队）
     */
    @PostMapping("/ms-team/check-create-team")
    @Timed
    public ResponseEntity<?> checkCreateTeam() throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("请登录"));
        }
        return teamRepository.findOneByCaptain(msAuthority.current())
            .map(team -> (ResponseEntity.ok(team)))
            .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 增加替补队员
     */
    @PostMapping("/ms-team/add-teamplayer")
    @Timed
    public ResponseEntity<?> addTeamplayer(@Valid @RequestBody TeamPlayerVM teamPlayerVM) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("请登录"));
        }
        return msTeamService.addTeamPlayer(teamPlayerVM);
    }

}
