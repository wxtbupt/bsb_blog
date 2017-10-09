package com.bisaibang.monojwt.web.rest.forum;


import com.bisaibang.monojwt.domain.forum.Forum;
import com.bisaibang.monojwt.repository.ForumRepository;
import com.bisaibang.monojwt.repository.SingleThreadRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.forum.MSSTService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * Created by ccy on 2017/2/5.
 */
@RestController
@RequestMapping("/api")
public class MSViewerSTResource {


    private final Logger log = LoggerFactory.getLogger(MSViewerSTResource.class);

    @Autowired
    private SingleThreadRepository sTRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSSTService msSTService;

    @Autowired
    private ForumRepository forumRepository;


    //获取帖子
    @GetMapping("/ms-singlethread/viewer/get-threads/forum/{forumid}")
    @Timed
    public ResponseEntity<?> getThreads(@PathVariable Long forumid, @Valid Pageable pageable) throws URISyntaxException {
        return ResponseEntity.ok(sTRepository.findAllByForumAndStateOrderByEditDateDesc(forumRepository.findOne(forumid), "normal", pageable));
    }


    //获取最后一页帖子
    @GetMapping("/ms-singlethread/viewer/get-threads/final-page/forum/{forumid}")
    @Timed
    public ResponseEntity<?> getThreadsFinalPage(@PathVariable Long forumid, @Valid Pageable pageable) throws URISyntaxException {
        int size = pageable.getPageSize();
        int finalPage;
        Forum f = forumRepository.findOne(forumid);

        int length = sTRepository.findAllByForumAndStateOrderByEditDateDesc(f, "normal").size();

        if (length % size == 0) {
            finalPage = length / size;
        } else {
            finalPage = length / size + 1;
        }

        Pageable finalPageable = new PageRequest(finalPage, size);
        return ResponseEntity.ok(sTRepository.findAllByForumAndStateOrderByEditDateDesc(f, "normal", finalPageable).getContent());
    }

}
