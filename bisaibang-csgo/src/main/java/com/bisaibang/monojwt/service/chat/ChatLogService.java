package com.bisaibang.monojwt.service.chat;

import com.bisaibang.monojwt.domain.chat.ChatLog;
import com.bisaibang.monojwt.repository.ChatLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing ChatLog.
 */
@Service
@Transactional
public class ChatLogService {

    private final Logger log = LoggerFactory.getLogger(ChatLogService.class);

    private final ChatLogRepository chatLogRepository;

    public ChatLogService(ChatLogRepository chatLogRepository) {
        this.chatLogRepository = chatLogRepository;
    }

    /**
     * Save a chatLog.
     *
     * @param chatLog the entity to save
     * @return the persisted entity
     */
    public ChatLog save(ChatLog chatLog) {
        log.debug("Request to save ChatLog : {}", chatLog);
        ChatLog result = chatLogRepository.save(chatLog);
        return result;
    }

    /**
     *  Get all the chatLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChatLog> findAll(Pageable pageable) {
        log.debug("Request to get all ChatLogs");
        Page<ChatLog> result = chatLogRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one chatLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ChatLog findOne(Long id) {
        log.debug("Request to get ChatLog : {}", id);
        ChatLog chatLog = chatLogRepository.findOne(id);
        return chatLog;
    }

    /**
     *  Delete the  chatLog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChatLog : {}", id);
        chatLogRepository.delete(id);
    }
}
