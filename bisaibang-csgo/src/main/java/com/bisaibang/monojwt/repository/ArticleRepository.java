package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.article.Article;

import com.bisaibang.monojwt.domain.people.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query("select article from Article article where article.creator.login = ?#{principal.username}")
    List<Article> findByCreatorIsCurrentUser();

    Page<Article> findAllByCreatorAndTypeOrderByIdDesc(User user, String type, Pageable pageable);

    Optional<Article> findOneById(Long id);

    Optional<Article> findOneByIdAndType(Long id, String type);

    List<Article> findAllByStateAndType(String state, String type);

    Page<Article> findAllByTypeOrderByIdDesc(String type, Pageable pageable);





}
