package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.domain.article.Comment;
import com.bisaibang.monojwt.domain.forum.Forum;

import com.bisaibang.monojwt.domain.people.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Forum entity.
 */
@SuppressWarnings("unused")
public interface ForumRepository extends JpaRepository<Forum,Long> {

    Optional<Forum> findOneByName(String name);
    Page<Forum> findAll (Pageable pageable);
}
