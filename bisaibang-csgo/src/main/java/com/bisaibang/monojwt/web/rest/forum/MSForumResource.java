package com.bisaibang.monojwt.web.rest.forum;


import com.bisaibang.monojwt.domain.forum.Forum;
import com.bisaibang.monojwt.repository.ForumRepository;
import com.bisaibang.monojwt.repository.PostRepository;
import com.bisaibang.monojwt.repository.SingleThreadRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.forum.MSPostService;
import com.codahale.metrics.annotation.Timed;
import net.logstash.logback.encoder.org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by ccy on 2017/2/5.
 */
@RestController
@RequestMapping("/api")
public class MSForumResource {
    private final Logger log = LoggerFactory.getLogger(MSForumResource.class);

    @Autowired
    private SingleThreadRepository sTRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSPostService msPostService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ForumRepository forumRepository;

    /**
     * 创建贴吧
     */

    @PostMapping("/ms-forum/create")
    @Timed
    public ResponseEntity<?> createForum(@Valid @RequestBody String forumname) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return forumRepository.findOneByName(forumname)
            .map(forum -> ResponseEntity.ok(message("名称已存在")))
            .orElseGet(() -> {
                String name = StringEscapeUtils.escapeSql(forumname);
                forumRepository.save(Forum.create(name));
                return ResponseEntity.ok(message("成功创建贴吧"));
            });
    }

    /**
     * 删除贴吧
     */
    @PostMapping("/ms-forum/delete/forum/{forumid}")
    @Timed
    public ResponseEntity<?> deleteForum(@PathVariable Long forumid) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        Forum f = forumRepository.findOne(forumid);
        f.deleteForum();
        forumRepository.save(f);
        return ResponseEntity.ok(message("贴吧已删除"));
    }

    /**
     * 更改贴吧信息  目前只有name,state
     */
    @PostMapping("/ms-forum/update/forum/{forumid}")
    @Timed
    public ResponseEntity<?> updateForum(@PathVariable Long forumid, @Valid @RequestBody Forum data) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        Forum f = forumRepository.findOne(forumid);
        Forum forum = msAuthority.forumSQLInject(data);
        f.setName(forum.getName());
        f.setState(forum.getState());
        forumRepository.save(f);
        return ResponseEntity.ok(message("成功修改贴吧"));
    }

    /**
     * 获取所有贴吧
     */
    @GetMapping("/ms-forum/get-all-forums")
    @Timed
    public ResponseEntity<?> getAllForums(@Valid Pageable pageable) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("请登录"));
        }
        Page<Forum> forums = forumRepository.findAll(pageable);
        return ResponseEntity.ok(forums.getContent());
    }

}
