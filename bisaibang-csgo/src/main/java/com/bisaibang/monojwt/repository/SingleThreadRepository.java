package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.forum.Forum;
import com.bisaibang.monojwt.domain.forum.SingleThread;

import com.bisaibang.monojwt.domain.people.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SingleThread entity.
 */
@SuppressWarnings("unused")
public interface SingleThreadRepository extends JpaRepository<SingleThread,Long> {


    //    @Query("select thread from Thread thread where thread.creator.login = ?#{principal.username}")
//    List<SingleThread> findByCreatorIsCurrentUser();
//
//    Page<SingleThread> findAllByCreatorOrderByIdDesc(User user, Pageable pageable);
//
//    Optional<SingleThread> findOneById(Long id);
//
//    SingleThread findOne(Long id);
//
//    Page<SingleThread> findAll(Specification<SingleThread> spec, Pageable pageable);
    List<SingleThread> findAllByForumAndStateOrderByEditDateDesc(Forum forum, String state);
    Page<SingleThread> findAllByForumAndStateOrderByEditDateDesc(Forum forum, String state, Pageable pageable);
    Page<SingleThread> findAllByForumAndStateOrderByIdDesc(Forum forum, String state, Pageable pageable);
    Page<SingleThread> findAllByForum(Forum forum, Pageable pageable);
    Page<SingleThread> findAllByForumOrderByIdDesc(Forum forum, Pageable pageable);
//    Page<SingleThread> findAllBySectionAndState(String section, String state, Pageable pageable);
//
//    List<SingleThread> findAllBySectionAndState(String section, String state);
//
//    Page<SingleThread> findAllBySectionAndStateOrderByIdDesc(String section, String state, Pageable pageable);
}
