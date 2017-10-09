package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.domain.article.Comment;
import com.bisaibang.monojwt.domain.video.VideoBroadcast;

import com.bisaibang.monojwt.domain.people.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select comment from Comment comment where comment.creator.login = ?#{principal.username}")
    List<Comment> findByCreatorIsCurrentUser();

    Page<Comment> findAllByCreatorOrderByIdDesc(User user, Pageable pageable);

    @Query("select comment from Comment comment where comment.id = ?1")
    Optional<Comment> findOneById(Long id);

    Page<Comment> findAllByArticleAndType(Article article, String type, Pageable pageable);

    Page<Comment> findAllByArticleAndLevelAndType(Article article, String level, String type, Pageable pageable);

    Page<Comment> findAllByLeaderCommentAndType(Comment comment,String type,  Pageable pageable);

    List<Comment> findAllByLeaderCommentAndType(Comment comment,String type);

    List<Comment> findAllByArticleAndType(Article article, String type);

    List<Comment> findAllByVideoAndType(VideoBroadcast videoBroadcast,String type);

    Page<Comment> findAllByVideoAndLevelAndType(VideoBroadcast videoBroadcast, String level, String type,  Pageable pageable);

    Page<Comment> findAllByVideoAndType(VideoBroadcast videoBroadcast, String type, Pageable pageable);


}
