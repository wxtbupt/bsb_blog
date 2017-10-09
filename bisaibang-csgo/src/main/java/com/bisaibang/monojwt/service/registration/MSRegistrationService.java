package com.bisaibang.monojwt.service.registration;

import com.bisaibang.monojwt.domain.game.Registration;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.team.TeamPlayer;
import com.bisaibang.monojwt.domain.util.ResponseMessage;
import com.bisaibang.monojwt.repository.RegistrationRepository;
import com.bisaibang.monojwt.repository.TeamPlayerRepository;
import com.bisaibang.monojwt.repository.TeamRepository;
import com.bisaibang.monojwt.repository.UserRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.generate.MailService;
import com.bisaibang.monojwt.service.util.SendMessageUtil;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bisaibang.monojwt.web.rest.generate.ResponseMessage.message;
import static java.util.stream.Collectors.toList;


/**
 * Created by liuchuang on 17/3/3.
 */
@Service
public class MSRegistrationService {


    private final org.slf4j.Logger log = LoggerFactory.getLogger(MSRegistrationService.class);

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private TeamRepository teamRepository;


    public ResponseEntity<?> createRegistration(Long teamid) {
        Team t = teamRepository.findOne(teamid);
        return registrationRepository.findOneByTeam(t)
            .map(registration -> {
                return ResponseEntity.ok(message("已经报名过"));
            })
            .orElseGet(() -> {
                registrationRepository.save(Registration.create(t));
                return ResponseEntity.ok(message("报名成功"));
            });
    }


    public ResponseEntity<?> createManyRegistrationByAdmin(List<Registration> registrations) {
        registrationRepository.save(registrations);
        return ResponseEntity.ok(message("存储成功"));
    }


    public String checkTeamName(String teamName) {
        return registrationRepository.findOneByTeamName(teamName)
            .map(r -> "teamName被占用")
            .orElseGet(() -> "teamName可用");
    }

    /**
     * 按照每组人数分组
     */
    public ResponseEntity<?> divideRegisterByGroupSize(Long maxGroupSize) {
        List<Registration> allR = registrationRepository.findAll();

        //组的数量
        Long groupNum = allR.size() / maxGroupSize;
        if (allR.size() % maxGroupSize != 0) {
            groupNum = allR.size() / maxGroupSize + 1;
        }

        //初始化每个R的group字段
        for (int i = 0; i < allR.size(); i++) {
            allR.get(i).setGroupId(-1L);
        }

        //初始化每个组的人数
        List<Long> groupSize = new ArrayList<Long>();
        for (int i = 0; i < groupNum; i++) {
            groupSize.add(0L);
        }
        for (int i = 0; i < allR.size(); i++) {
            Long g = (long) (groupNum * Math.random());
            if (groupSize.get(g.intValue()) < maxGroupSize) {
                allR.get(i).setGroupId(g + 1);
                groupSize.set(g.intValue(), groupSize.get(g.intValue()) + 1);
            } else {
                i--;
            }
        }

        registrationRepository.save(allR);
        return ResponseEntity.ok(message("分组完毕"));
    }


    /**
     * 按照组数分组
     */
    public ResponseEntity<?> divideRegisterByGroupNum(Long groupNum) {
        List<Registration> allR = registrationRepository.findAll();

        //每组人数
        Long maxGroupSize = allR.size() / groupNum;
        if (allR.size() % groupNum != 0) {
            maxGroupSize = allR.size() / groupNum + 1;
        }

        //初始化每个R的group字段
        for (int i = 0; i < allR.size(); i++) {
            allR.get(i).setGroupId(-1L);
        }

        //初始化每个组的人数
        List<Long> groupSize = new ArrayList<Long>();
        for (int i = 0; i < groupNum; i++) {
            groupSize.add(0L);
        }
        for (int i = 0; i < allR.size(); i++) {
            Long g = (long) (groupNum * Math.random());
            if (groupSize.get(g.intValue()) < maxGroupSize) {
                allR.get(i).setGroupId(g + 1);
                groupSize.set(g.intValue(), groupSize.get(g.intValue()) + 1);
            } else {
                i--;
            }
        }
        registrationRepository.save(allR);
        return ResponseEntity.ok(message("分组完毕"));
    }


    public ResponseEntity<?> deleteRegistrationByTeam(Long teamid) {
        Team team = teamRepository.findOne(teamid);
       return  registrationRepository.findOneByTeam(team)
            .map(registration -> {
                registrationRepository.delete(registration);
                return ResponseEntity.ok(message("退出报名成功"));
            })
            .orElseGet(() ->{return ResponseEntity.ok(message("小队未报名"));
       });
    }



    public ResponseEntity<?> sendSuccessMessage() {
        registrationRepository.findAll().forEach(
            registration -> {
                Team t = registration.getTeam();
                String phones = teamPlayerRepository.findAllByTeam(t)
                    .stream()
                    .map(teamPlayer -> {
                        return teamPlayer.getPhone();
                   })
                    .reduce((v, u) -> v + "," + u).get();
                SendMessageUtil.sendEnrollOKMessage(phones);
           });
        return ResponseEntity.ok(message("发送成功"));
    }




}
