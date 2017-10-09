package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.forum.Post;

import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.people.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Post entity.
 */
@SuppressWarnings("unused")
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select post from Post post where post.creator.login = ?#{principal.username}")
    List<Post> findByCreatorIsCurrentUser();
    //    List<Post> findByCreatorIsCurrentUser();

    Optional<Post> findOneById(Long id);

    //
//    Page<Post> findAllByCreatorOrderByIdDesc(User user, Pageable pageable);
//
//    Page<Post> findAllByThreadOrderByEditDateDesc(SingleThread thread, Pageable pageable);
//
//    Page<Post> findAllByThreadAndLevelOrderByEditDateDesc(SingleThread thread, String level, Pageable pageable);
//
//    Page<Post> findAllByLeaderPostOrderByEditDateDesc(Post post, Pageable pageable);
//
    Page<Post> findAllBySingleThread(SingleThread singleThread, Pageable pageable);

    Page<Post> findAllBySingleThreadAndState(SingleThread singleThread, String state, Pageable pageable);


}
