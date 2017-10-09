package com.bisaibang.monojwt.service.article;

import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.domain.article.Comment;
import com.bisaibang.monojwt.domain.video.VideoBroadcast;
import com.bisaibang.monojwt.repository.ArticleRepository;
import com.bisaibang.monojwt.repository.CommentRepository;
import com.bisaibang.monojwt.repository.VideoBroadcastRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.ZonedDateTime;
import java.util.List;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;
import static java.util.stream.Collectors.toList;

/**
 * Created by ccy on 2017/2/5.
 */
@Service
public class MSCommentService {

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private VideoBroadcastRepository videoBroadcastRepository;


    public ResponseEntity<?> createComment(Comment comment) {
        comment = msAuthority.commentSQLInject(comment);
        Comment com = Comment.create(comment);
        com.setCreator(msAuthority.current());
        com.setAuthorName(msAuthority.current().getNickName());
        commentRepository.save(com);
        Article a = articleRepository.findOne(com.getArticle().getId());
        a.incCommentNumber();
        articleRepository.save(a);
        return ResponseEntity.ok(message("comment创建成功"));
    }

    public ResponseEntity<?> createCommentVideo(Comment comment) {
        comment = msAuthority.commentSQLInject(comment);
        Comment com = Comment.create(comment);
        com.setCreator(msAuthority.current());
        com.setAuthorName(msAuthority.current().getNickName());
        commentRepository.save(com);
        VideoBroadcast v = videoBroadcastRepository.findOne(com.getVideo().getId());
        v.incCommentNumber();
        videoBroadcastRepository.save(v);
        return ResponseEntity.ok(message("comment创建成功"));
    }



    public ResponseEntity<?> deleteComment(Long id) {
        Comment c = commentRepository.findOne(id);
        c.deleteComment();
        commentRepository.save(c);
        List<Comment> comments = commentRepository.findAllByLeaderCommentAndType(c,"normal").stream()
            .map(comment -> {
                comment.deleteComment();
                commentRepository.save(comment);
                return comment;
            })
            .collect(toList());
        return ResponseEntity.ok(message("评论删除成功"));
    }

    public ResponseEntity<?> getCommentsByArticle(Long id, Pageable pageable) {
        return ResponseEntity.ok(commentRepository.findAllByArticleAndType(articleRepository.findOne(id),"normal", pageable));
    }

    public ResponseEntity<?> getCommentsByVideo(Long videoid, Pageable pageable) {
        return ResponseEntity.ok(commentRepository.findAllByVideoAndType(videoBroadcastRepository.findOne(videoid),"normal", pageable));
    }

    public ResponseEntity<?> getCommentsByArticleAndLevel(Long id, String level, Pageable pageable) {
        return ResponseEntity.ok(commentRepository.findAllByArticleAndLevelAndType(articleRepository.findOne(id), level,"normal", pageable));
    }

    public ResponseEntity<?> getCommentsByVideoAndLevel(Long id, String level, Pageable pageable) {
        return ResponseEntity.ok(commentRepository.findAllByArticleAndLevelAndType(articleRepository.findOne(id), level,"normal", pageable));
    }

    public ResponseEntity<?> findAllCommentByLeaderComment(Long id, Pageable pageable) {
        return ResponseEntity.ok(commentRepository.findAllByLeaderCommentAndType(commentRepository.findOne(id), "normal", pageable));
    }


}
