package com.bisaibang.monojwt.web.rest.article;

import com.bisaibang.monojwt.domain.article.Comment;
import com.bisaibang.monojwt.repository.CommentRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.article.MSCommentService;
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
public class MSCommentResource {

    private final Logger log = LoggerFactory.getLogger(MSCommentResource.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSCommentService msCommentService;


    /**
     * 创建comment article
     */
    @RequestMapping(value = "/ms-comment/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createComment(@RequestBody Comment comment) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        return msCommentService.createComment(comment);
    }

    /**
     * 创建comment Video
     */
    @RequestMapping(value = "/ms-comment/create/video",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createCommentVideo(@RequestBody Comment comment) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        return msCommentService.createCommentVideo(comment);
    }


    /**
     * 删除comment by admin
     */
    @PostMapping(path = "/ms-comment/admin/delete/comment/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> deleteCommentByAdmin(@PathVariable Long id) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msCommentService.deleteComment(id);
    }


    /**
     * 获取一个Article的所有comment
     */
    @RequestMapping(path = "/ms-comment/admin/get-all-comments/article/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getCommentsByArticle(@PathVariable Long id, @Valid Pageable pageable) throws URISyntaxException {
        return msCommentService.getCommentsByArticle(id, pageable);
    }

    /**
     * 获取一个Video的所有comment
     */
    @RequestMapping(path = "/ms-comment/get-all-comments/video/{videoid}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getCommentsByVideo(@PathVariable Long videoid, @Valid Pageable pageable) throws URISyntaxException {
        return msCommentService.getCommentsByVideo(videoid, pageable);
    }


    /**
     * get all comment by article and level
     */
    @RequestMapping(path = "/ms-comment/get-comments/article/{id}/level/{level}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getCommentByArticleAndLevel(@PathVariable Long id, @PathVariable String level, @Valid Pageable pageable) throws URISyntaxException {
        return msCommentService.getCommentsByArticleAndLevel(id, level, pageable);
    }

    /**
     * get all comment by video and level
     */
    @RequestMapping(path = "/ms-comment/get-comments/video/{id}/level/{level}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getCommentByVideoAndLevel(@PathVariable Long id, @PathVariable String level, @Valid Pageable pageable) throws URISyntaxException {
        return msCommentService.getCommentsByVideoAndLevel(id, level, pageable);
    }



    /**
     * get all comment by leaderComment
     */
    @RequestMapping(path = "/ms-comment/all-comment/leader-comment/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAllCommentByLeaderComment(@PathVariable Long id, @Valid Pageable pageable) throws URISyntaxException {
        return msCommentService.findAllCommentByLeaderComment(id, pageable);
    }







}
