package com.bisaibang.monojwt.web.rest.forum;


import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.vm.IdListVM;
import com.bisaibang.monojwt.repository.ForumRepository;
import com.bisaibang.monojwt.repository.SingleThreadRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.forum.MSSTService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import java.net.URISyntaxException;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by ccy on 2017/2/5.
 */
@RestController
@RequestMapping("/api")
public class MSSTResource {

    private final Logger log = LoggerFactory.getLogger(MSSTResource.class);

    @Autowired
    private SingleThreadRepository sTRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSSTService bsbSTService;

    @Autowired
    private ForumRepository forumRepository;

    //创建thread
    @PostMapping("/ms-singlethread/create/forum/{forumid}")
    @Timed
    public ResponseEntity<?> createThread(@PathVariable Long forumid, @Valid @RequestBody SingleThread thread) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        return bsbSTService.createThread(forumid, thread);
    }

    /**
     * 删除thread
     */
    @PostMapping("/ms-singlethread/delete/single-thread/{threadid}")
    @Timed
    public ResponseEntity<?> deleteThread(@PathVariable Long threadid) throws URISyntaxException {
        SingleThread thread = sTRepository.findOne(threadid);
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        thread.deleteSingleThread();
        sTRepository.save(thread);
        return ResponseEntity.ok(message("成功"));
    }

    /**
     * 获取被删除贴子
     */
    @GetMapping("/ms-singlethread/get-threads/forum/{forumid}")
    @Timed
    public ResponseEntity<?> getThreads(@PathVariable Long forumid, @Valid Pageable pageable) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return ResponseEntity.ok(sTRepository.findAllByForumAndStateOrderByIdDesc(forumRepository.findOne(forumid), "delete", pageable));
    }

    /**
     * 帖子置顶
     */
    @PostMapping("/ms-singlethread/essential/thread/{threadid}")
    @Timed
    public ResponseEntity<?> markThread(@PathVariable Long threadid) throws URISyntaxException {
        SingleThread sT = sTRepository.findOne(threadid);
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return bsbSTService.markThread(threadid);
    }

    /**
     * 帖子不置顶
     */
    @PostMapping("/ms-singlethread/revert-essential/thread/{threadid}")
    @Timed
    public ResponseEntity<?> revertMarkThread(@PathVariable Long threadid) throws URISyntaxException {
        SingleThread sT = sTRepository.findOne(threadid);
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return bsbSTService.revertMarkThread(threadid);
    }

    /**
     * 获取置顶帖子
     */
    @GetMapping("/ms-singlethread/get-mark-threads/forum/{forumid}")
    @Timed
    public ResponseEntity<?> getMarkThreads(@PathVariable Long forumid, @Valid Pageable pageable) throws URISyntaxException {
        return ResponseEntity.ok(sTRepository.findAllByForumAndStateOrderByIdDesc(forumRepository.findOne(forumid), "essence", pageable));
    }

    /**
     * 批量删除thread
     */
    @RequestMapping(value = "/ms-singlethread/thread/batch-delete-threads",
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> batchDeleteThreads(@Valid @RequestBody IdListVM idList) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return bsbSTService.batchDelete(idList.getList());
    }


}
