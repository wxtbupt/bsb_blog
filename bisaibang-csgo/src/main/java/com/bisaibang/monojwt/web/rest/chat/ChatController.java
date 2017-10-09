package com.bisaibang.monojwt.web.rest.chat;

import com.bisaibang.monojwt.domain.chat.ChatLog;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.web.rest.article.MSCommentResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.time.ZonedDateTime;

@Controller
public class ChatController {
    private final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private MSAuthority msAuthority;

    @Autowired

    public SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/wschat/global")
    @SendTo("/messenger/global")
    public ChatLog sendGlobalMessage(ChatLog message) throws Exception {
        log.error(msAuthority.current().toString());
        message.setDate(ZonedDateTime.now());
        return message;
    }

    public void sendAll(String content, Long id) throws Exception {
        log.error(msAuthority.current().toString());
        log.error(template.toString());
        template.convertAndSend("/messenger/" + id, ChatLog.createTest(content));
    }


}
