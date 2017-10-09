package com.bisaibang.monojwt.service.video;

import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.video.VideoBroadcast;
import com.bisaibang.monojwt.repository.CommentRepository;
import com.bisaibang.monojwt.repository.VideoBroadcastRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.web.rest.video.MSVideoBroadcastResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuang on 17/3/6.
 */
@Service
public class MSVideoBroadcastService {

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private VideoBroadcastRepository videoBroadcastRepository;

    @Autowired
    private CommentRepository commentRepository;

    public ResponseEntity<?> createVideoBroadcast(VideoBroadcast videoBroadcast) {
        videoBroadcast = msAuthority.VBSQLInject(videoBroadcast);
        VideoBroadcast video = VideoBroadcast.create(videoBroadcast.getName(), videoBroadcast.getUrl(), videoBroadcast.getTag(), videoBroadcast.getState(), videoBroadcast.getThumbnailUrl(), videoBroadcast.getIntroduction());
        videoBroadcastRepository.save(video);
        return ResponseEntity.ok(message("创建成功"));
    }

    public ResponseEntity<?> deleteVideoBroadcast(Long id) {
        VideoBroadcast video = videoBroadcastRepository.findOne(id);
        video.deleteVideo();
        videoBroadcastRepository.save(video);
        return ResponseEntity.ok(message("废弃成功"));
    }

    public ResponseEntity<?> editVideoBroadcast(Long id, VideoBroadcast data) {

        VideoBroadcast video = videoBroadcastRepository.findOne(id);
        if (video.getType().equals("delete")) {
            return ResponseEntity.ok(message("已经废弃，不能修改"));
        }
        data = msAuthority.VBSQLInject(data);
        video.setName(data.getName());
        video.setState(data.getState());
        video.setTag(data.getTag());
        video.setUrl(data.getUrl());
        video.setThumbnailUrl(data.getThumbnailUrl());
        video.setIntroduction(data.getIntroduction());
        videoBroadcastRepository.save(video);
        return ResponseEntity.ok(message("更新成功"));
    }


    public ResponseEntity<?> deleteManyVideo(List<Long> idList) {
        for (Long id : idList) {
            VideoBroadcast video = videoBroadcastRepository.findOne(id);
            video.deleteVideo();
            videoBroadcastRepository.save(video);
        }
        return ResponseEntity.ok(message("批量删除成功"));
    }

}
