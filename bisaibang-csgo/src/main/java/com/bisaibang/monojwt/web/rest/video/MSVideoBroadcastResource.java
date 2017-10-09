package com.bisaibang.monojwt.web.rest.video;


import com.bisaibang.monojwt.domain.video.VideoBroadcast;
import com.bisaibang.monojwt.repository.VideoBroadcastRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.video.MSVideoBroadcastService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by liuchuang on 17/3/5.
 */
@RestController
@RequestMapping("/api")
public class MSVideoBroadcastResource {

    private final Logger log = LoggerFactory.getLogger(MSVideoBroadcastResource.class);

    @Autowired
    private VideoBroadcastRepository videoBroadcastRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSVideoBroadcastService msVideoBroadcastService;

    //创建Video
    @RequestMapping(value = "/ms-video/create",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createVideoBroadcast(@RequestBody VideoBroadcast videoBroadcast) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msVideoBroadcastService.createVideoBroadcast(videoBroadcast);
    }

    //删除Video
    @PostMapping(path = "/ms-video/delete/video/{id}",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> deleteVideoBroadcast(@PathVariable Long id) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msVideoBroadcastService.deleteVideoBroadcast(id);
    }

    //编辑video
    @RequestMapping(value = "/ms-video/edit/video/{id}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> editVideoBroadcast(@PathVariable Long id, @RequestBody VideoBroadcast videoBroadcast) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msVideoBroadcastService.editVideoBroadcast(id, videoBroadcast);
    }

    //获取所有video
    @RequestMapping(value = "/ms-video/get-all-video",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAllVideo(@Valid Pageable page) throws URISyntaxException {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), new Sort(orders));

        return ResponseEntity.ok(videoBroadcastRepository.findAllByType("normal", pageable));
    }

    //获取video by id
    @RequestMapping(value = "/ms-video/get-video/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getVideoById(@PathVariable Long id) throws URISyntaxException {
        return videoBroadcastRepository.findOneByIdAndType(id, "normal")
            .map(video -> {
                return ResponseEntity.ok(video);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    //删除批量video
    @RequestMapping(value = "/ms-video/delete-many",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> deleteManyVideo(@Valid @RequestBody List<Long> idList) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msVideoBroadcastService.deleteManyVideo(idList);
    }
}
