package com.bisaibang.monojwt.web.rest.registration;

import com.bisaibang.monojwt.domain.forum.Forum;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.vm.UserVM;
import com.bisaibang.monojwt.repository.RegistrationRepository;
import com.bisaibang.monojwt.repository.UserRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.registration.MSPersonInfoService;
import com.bisaibang.monojwt.service.registration.MSRegistrationService;
import com.bisaibang.monojwt.web.rest.generate.ResponseMessage;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.net.URISyntaxException;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

@RestController
@RequestMapping("/api")
public class MSPersonInfoResource {

    private final Logger log = LoggerFactory.getLogger(MSPersonInfoResource.class);

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSRegistrationService msRegistrationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MSPersonInfoService msPersonInfoService;

    /**
     * 更改个人信息
     */
    @PostMapping("/ms-person-info/update")
    @Timed
    public ResponseEntity<?> updatePersonInfo(@Valid @RequestBody UserVM data) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        User u = msAuthority.current();
        data = msAuthority.userVMSQLInject(data);
        u.setNickName(data.getNickname());
        u.setPersonalEmail(data.getEmail());
        u.setPersonalID(data.getPersonalId());
        u.setFirstName(data.getName());
        userRepository.save(u);
        return ResponseEntity.ok(message("成功修改个人信息"));
    }

    /**
     * 给所有已经注册用户发短信
     */
    @PostMapping("/ms-person-info/send-enroll-message/")
    @Timed
    public ResponseEntity<?> sendEnrollMessage() throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(ResponseMessage.message("权限不足"));
        }
        return msPersonInfoService.sendEnrollMessage();
    }

    /**
     * 给所有绑定了战网id的用户发短信
     */
    @PostMapping("/ms-person-info/send-battle-message/")
    @Timed
    public ResponseEntity<?> sendBattleMessage() throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(ResponseMessage.message("权限不足"));
        }
        return msPersonInfoService.sendBattleMessage();
    }

}
