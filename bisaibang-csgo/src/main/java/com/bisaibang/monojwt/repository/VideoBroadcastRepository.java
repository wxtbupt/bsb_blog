package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.video.VideoBroadcast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
 * Created by liuchuang on 17/3/5.
 */
public interface VideoBroadcastRepository extends JpaRepository<VideoBroadcast, Long> {

    VideoBroadcast findOne(Long id);

    Optional<VideoBroadcast> findOneByIdAndType(Long id, String type);

    Page<VideoBroadcast> findAllByType(Specification<VideoBroadcast> spec, String type, Pageable pageable);

    Page<VideoBroadcast> findAllByOrderByIdDesc(Pageable pageable);

    Page<VideoBroadcast> findAllByOrderByIdDesc(Specification<VideoBroadcast> spec, Pageable pageable);

    Page<VideoBroadcast> findAllByType( String type, Pageable pageable);
}
