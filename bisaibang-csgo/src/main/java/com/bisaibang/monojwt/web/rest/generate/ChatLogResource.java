package com.bisaibang.monojwt.web.rest.generate;

import com.codahale.metrics.annotation.Timed;
import com.bisaibang.monojwt.domain.chat.ChatLog;
import com.bisaibang.monojwt.service.chat.ChatLogService;
import com.bisaibang.monojwt.web.rest.generate.util.HeaderUtil;
import com.bisaibang.monojwt.web.rest.generate.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChatLog.
 */
@RestController
@RequestMapping("/api")
public class ChatLogResource {

    private final Logger log = LoggerFactory.getLogger(ChatLogResource.class);

    private static final String ENTITY_NAME = "chatLog";

    private final ChatLogService chatLogService;

    public ChatLogResource(ChatLogService chatLogService) {
        this.chatLogService = chatLogService;
    }

    /**
     * POST  /chat-logs : Create a new chatLog.
     *
     * @param chatLog the chatLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chatLog, or with status 400 (Bad Request) if the chatLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chat-logs")
    @Timed
    public ResponseEntity<ChatLog> createChatLog(@RequestBody ChatLog chatLog) throws URISyntaxException {
        log.debug("REST request to save ChatLog : {}", chatLog);
        if (chatLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new chatLog cannot already have an ID")).body(null);
        }
        ChatLog result = chatLogService.save(chatLog);
        return ResponseEntity.created(new URI("/api/chat-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chat-logs : Updates an existing chatLog.
     *
     * @param chatLog the chatLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chatLog,
     * or with status 400 (Bad Request) if the chatLog is not valid,
     * or with status 500 (Internal Server Error) if the chatLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chat-logs")
    @Timed
    public ResponseEntity<ChatLog> updateChatLog(@RequestBody ChatLog chatLog) throws URISyntaxException {
        log.debug("REST request to update ChatLog : {}", chatLog);
        if (chatLog.getId() == null) {
            return createChatLog(chatLog);
        }
        ChatLog result = chatLogService.save(chatLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chatLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chat-logs : get all the chatLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chatLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/chat-logs")
    @Timed
    public ResponseEntity<List<ChatLog>> getAllChatLogs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ChatLogs");
        Page<ChatLog> page = chatLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chat-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chat-logs/:id : get the "id" chatLog.
     *
     * @param id the id of the chatLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chatLog, or with status 404 (Not Found)
     */
    @GetMapping("/chat-logs/{id}")
    @Timed
    public ResponseEntity<ChatLog> getChatLog(@PathVariable Long id) {
        log.debug("REST request to get ChatLog : {}", id);
        ChatLog chatLog = chatLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chatLog));
    }

    /**
     * DELETE  /chat-logs/:id : delete the "id" chatLog.
     *
     * @param id the id of the chatLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chat-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteChatLog(@PathVariable Long id) {
        log.debug("REST request to delete ChatLog : {}", id);
        chatLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
