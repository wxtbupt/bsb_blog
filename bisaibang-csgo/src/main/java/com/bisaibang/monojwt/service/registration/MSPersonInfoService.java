package com.bisaibang.monojwt.service.registration;

import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.domain.game.Registration;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.util.ResponseMessage;
import com.bisaibang.monojwt.domain.vm.RegistrationVM;
import com.bisaibang.monojwt.repository.*;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.generate.MailService;
import com.bisaibang.monojwt.service.util.SendMessageUtil;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.bisaibang.monojwt.service.util.SendCloud.SendCloudMailService.send_common;
import static com.bisaibang.monojwt.web.rest.generate.ResponseMessage.message;



@Service
public class MSPersonInfoService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(MSPersonInfoService.class);

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private MSAuthority msAuthority;


    public ResponseEntity<?> sendEnrollMessage() {
        String phones = userRepository.findAll()
            .stream()
            .filter(u -> u.getId() > 8L)
            .map(u -> {
                return u.getFullPhone();
            })
            .reduce((v, u) -> v + "," + u).get();
        SendMessageUtil.sendRegisterOKMessage(phones);
        return ResponseEntity.ok(message("发送成功"));
    }

    public ResponseEntity<?> sendBattleMessage() {
        String phones = userRepository.findAll()
            .stream()
            .filter(u -> u.hasNickName() && u.getId() > 8L)
            .map(u -> {
                return u.getFullPhone();
            })
            .reduce((v, u) -> v + "," + u).get();
        SendMessageUtil.sendBattleMessage(phones);
        return ResponseEntity.ok(ResponseMessage.message("发送成功"));
    }


}
