package com.bisaibang.monojwt.web.rest.registration;

import com.bisaibang.monojwt.domain.game.Card;
import com.bisaibang.monojwt.domain.game.Registration;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.team.TeamPlayer;
import com.bisaibang.monojwt.domain.util.ResponseMessage;
import com.bisaibang.monojwt.domain.vm.RegistrationVM;
import com.bisaibang.monojwt.repository.*;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.registration.MSRegistrationService;
import com.bisaibang.monojwt.web.rest.generate.RegistrationResource;

import static com.bisaibang.monojwt.web.rest.generate.ResponseMessage.message;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liuchuang on 17/2/25.
 */
@RestController
@RequestMapping("/api")
public class MSRegistrationResource {


    private final Logger log = LoggerFactory.getLogger(RegistrationResource.class);

    private static final String ENTITY_NAME = "registration";

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSRegistrationService msRegistrationService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;


    @PostMapping("/ms-registrations/create/{teamid}")
    @Timed
    public ResponseEntity<?> createRegistration(@PathVariable Long teamid) throws URISyntaxException {
        if (!msAuthority.isCaptain(teamid)) {
            return ResponseEntity.ok(message("非队长不能报名"));
        }
        if (!msAuthority.isNumberEnough(teamid)) {
            return ResponseEntity.ok(message("人数不足"));
        }
        return msRegistrationService.createRegistration(teamid);
    }


    /**
     * 获取所有报名信息
     */
    @GetMapping("/ms-registrations/get-all-registrations")
    @Timed
    public ResponseEntity<?> getAllRegistrations(@Valid Pageable pageable) throws URISyntaxException {
        Page<Registration> registrationPage = registrationRepository.findAll(pageable);
        return ResponseEntity.ok(
            registrationPage.getContent().stream()
                .map(registration -> {
                    Team t = registration.getTeam();
                    List<TeamPlayer> teamPlayers = teamPlayerRepository.findAllByTeam(t);
                    RegistrationVM.create(registration, teamPlayers);
                    return registration;
                })
                .collect(Collectors.toList()));
    }


    /**
     * 按组号返回 某一组的报名情况
     */
    @GetMapping("/ms-registrations/get-registrations/groupId/{groupid}")
    @Timed
    public ResponseEntity<?> getRegistrationsByGroupId(@PathVariable Long groupid) throws URISyntaxException {
        List<Registration> registrations = registrationRepository.findAllByGroupId(groupid);
        return ResponseEntity.ok(
            registrations.stream()
                .map(registration -> {
                    Team t = registration.getTeam();
                    List<TeamPlayer> teamPlayers = teamPlayerRepository.findAllByTeam(t);
                    RegistrationVM.create(registration, teamPlayers);
                    return registration;
                })
                .collect(Collectors.toList()));
    }


    /**
     * check the teamName
     */
    @GetMapping("/ms-registrations/check/team-name/{teamname}")
    @Timed
    public ResponseEntity<?> checkTeamName(@PathVariable String teamname) throws URISyntaxException {
        return ResponseEntity.ok(message(msRegistrationService.checkTeamName(teamname)));
    }

    /**
     * change the teamName
     */
    @PostMapping("/ms-registrations/change/team-name/{oldteamname}/{newteamname}")
    @Timed
    public ResponseEntity<?> changeTeamName(@PathVariable String oldteamname, @PathVariable String newteamname) throws URISyntaxException {
        return teamRepository.findOneByName(oldteamname)
            .map(team -> {
                if (!msAuthority.isTeamManager(team.getId())) {
                    return ResponseEntity.ok(message("越权"));
                }
                return teamRepository.findOneByName(newteamname)
                    .map(team1 -> {
                        return ResponseEntity.ok(message("队伍名已存在"));
                    })
                    .orElseGet(() -> {
                        team.setName(newteamname);
                        teamRepository.save(team);
                        return ResponseEntity.ok(message("小队名修改成功"));
                    });
            })
            .orElseGet(() -> ResponseEntity.ok(message("小队不存在")));
    }



    /**
     * 战网Id修改
     */
    @PostMapping("/ms-registrations/change/battleid/{oldnickname}/{newnickname}")
    @Timed
    public ResponseEntity<?> changeBattleId(@PathVariable String oldnickname, @PathVariable String newnickname) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return userRepository.findOneByNickName(oldnickname)
            .map(u -> {
                if (msAuthority.isNickNameExist(newnickname)) {
                    return ResponseEntity.ok(message("战网id已被占用"));
                }
                u.setNickName(newnickname);
                userRepository.save(u);
                return ResponseEntity.ok(message("战网id已修改"));
            })
            .orElseGet(() -> ResponseEntity.ok(message("战网id不存在")));
    }


    /**
     * 检测一个战网id是否存在
     */
    @GetMapping("/ms-registrations/check/battle-id/{battleid}")
    @Timed
    public ResponseEntity<?> checkBattleId(@PathVariable String battleid) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        if (msAuthority.isNickNameExist(battleid)) {
            return ResponseEntity.ok(message("战网id已被占用"));
        }
        return ResponseEntity.ok(message("战网id可用"));
    }


    /**
     * 自己解绑战网id
     */
    @PostMapping("/ms-registrations/delete/battle-id")
    @Timed
    public ResponseEntity<?> deleteBattleId() throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        User u = msAuthority.current();
        u.clean();
        return ResponseEntity.ok(userRepository.save(u));
    }



    /**
     * 分组算法，按照每组人数分组（暂时保留）
     */
    @PostMapping("/ms-registrations/admin/divide-register-into-group/{groupsize}")
    @Timed
    public ResponseEntity<?> divideRegisterByGroupSize(@PathVariable Long groupSize) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return msRegistrationService.divideRegisterByGroupSize(groupSize);
    }

    /**
     * 分组算法，按照组数分组（新需求）
     */
    @PostMapping("/ms-registrations/admin/divide-register-into-group/group/{num}")
    @Timed
    public ResponseEntity<?> divideRegisterByGroupNum(@PathVariable Long groupNum) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return msRegistrationService.divideRegisterByGroupNum(groupNum);
    }


    @PostMapping("/ms-registrations/admin/create/card")
    @Timed
    public ResponseEntity<?> createCardAdmin(@Valid @RequestBody Card card) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        card.setRegistration(registrationRepository.findOne(card.getRegistration().getId()));
        cardRepository.save(card);
        return ResponseEntity.ok(message("成功"));
    }

    @PostMapping("/ms-registrations/admin/delete/card")
    @Timed
    public ResponseEntity<?> deleteCardAdmin(@Valid @RequestBody Long cardid) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        cardRepository.delete(cardid);
        return ResponseEntity.ok(message("成功"));
    }

    @PostMapping("/ms-registrations/get-my/card")
    @Timed
    public ResponseEntity<?> getMyCard(@Valid @RequestBody Long rId) throws URISyntaxException {
        return ResponseEntity.ok(cardRepository.findAllByRegistration(registrationRepository.findOne(rId)));
    }

    /**
     * 退出报名
     */
    @PostMapping("/ms-registrations/leave/team/{teamid}")
    @Timed
    public ResponseEntity<?> deleteRegistration(@PathVariable Long teamid) throws URISyntaxException {
        if (!msAuthority.isTeamManager(teamid)) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msRegistrationService.deleteRegistrationByTeam(teamid);
    }

    /**
     * 给报名成功的所有小队队员短信
     */
    @PostMapping("/ms-registrations/send-message/success")
    @Timed
    public ResponseEntity<?> sendSuccessMessage() throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msRegistrationService.sendSuccessMessage();
    }


}
