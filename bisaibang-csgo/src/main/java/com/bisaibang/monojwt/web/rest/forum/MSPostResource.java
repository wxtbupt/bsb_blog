package com.bisaibang.monojwt.web.rest.forum;


import com.bisaibang.monojwt.domain.forum.Post;
import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.vm.STPostVM;
import com.bisaibang.monojwt.repository.PostRepository;
import com.bisaibang.monojwt.repository.SingleThreadRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.forum.MSPostService;
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

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by ccy on 2017/2/5.
 */
@RestController
@RequestMapping("/api")
public class MSPostResource {
    private final Logger log = LoggerFactory.getLogger(MSPostResource.class);

    @Autowired
    private SingleThreadRepository sTRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSPostService msPostService;

    @Autowired
    private PostRepository postRepository;


    /**
     * 创建Post
     * @param threadid
     * @param post
     * @return
     * @throws URISyntaxException
     */

    @PostMapping("/ms-post/create/single-thread/{threadid}")
    @Timed
    public ResponseEntity<?> createPost(@PathVariable Long threadid, @Valid @RequestBody Post post) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        return msPostService.createPost(threadid, post);
    }

    //获取一个SingleThread的所有post
    @GetMapping("/ms-post/all-post/single-thread/{threadid}")
    @Timed
    public ResponseEntity<?> findAllPostByThread(@PathVariable Long threadid, @Valid Pageable pageable) throws URISyntaxException {
        SingleThread t = sTRepository.findOne(threadid);
        if (t.getState().equals("delete")) {
            return ResponseEntity.ok(message("该贴已被删除"));
        }
        Page<Post> postPage = postRepository.findAllBySingleThreadAndState(t, "normal", pageable);
        return ResponseEntity.ok(STPostVM.create(t, postPage));
    }

    /**
     * 删除回复
     */
    @PostMapping("/ms-post/thread/delete/post/{postid}")
    @Timed
    public ResponseEntity<?> deletePost(@PathVariable Long postid) throws URISyntaxException {
        Post post = postRepository.findOne(postid);
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        post.deletePost();
        postRepository.save(post);
        return ResponseEntity.ok(message("成功"));
    }

}
