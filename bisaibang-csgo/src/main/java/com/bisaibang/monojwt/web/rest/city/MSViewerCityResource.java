package com.bisaibang.monojwt.web.rest.city;

import com.bisaibang.monojwt.domain.team.City;
import com.bisaibang.monojwt.domain.team.CityMember;
import com.bisaibang.monojwt.domain.vm.CityVM;
import com.bisaibang.monojwt.repository.*;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.util.SendMessageUtil;
import com.bisaibang.monojwt.web.rest.generate.ResponseMessage;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URISyntaxException;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by liujinduo on 2016/10/26.
 */
@RestController
@RequestMapping("/api")
public class MSViewerCityResource {

    private final Logger log = LoggerFactory.getLogger(MSViewerCityResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMemberRepository cityMemberRepository;

    @PostMapping("/ms-city/enter/city/{cityid}")
    @Timed
    public ResponseEntity<?> enterCity(@PathVariable Long cityid) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        City city = cityRepository.findOne(cityid);
        cityMemberRepository.save(CityMember.create(city,msAuthority.current()));
        return ResponseEntity.ok(ResponseMessage.message("成功"));

    }

    /**
     * 获取所有city
     */
    @GetMapping("/ms-city/viewer/get-all-city")
    @Timed
    public ResponseEntity<?> getAllCity(@Valid Pageable pageable) throws URISyntaxException {
        return ResponseEntity.ok(cityRepository.findAll(pageable));
    }

    /**
     * 获取该city的所有member
     */
    @GetMapping("/ms-city/viewer/get-all-member/city/{cityid}")
    @Timed
    public ResponseEntity<?> getAllMember(@PathVariable Long cityid, @Valid Pageable pageable) throws URISyntaxException {
        City city = cityRepository.findOne(cityid);
        return ResponseEntity.ok(cityMemberRepository.findAllByCity(city, pageable));
    }

    /**
     * 一个member退出一个city
     */
    @PostMapping("/ms-city/viewer/leave-city/city/{cityid}")
    @Timed
    public ResponseEntity<?> leaveCity(@PathVariable Long cityid) throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("未登录"));
        }
        City city = cityRepository.findOne(cityid);
        return cityMemberRepository.findOneByCityAndUser(city,msAuthority.current())
            .map(cityMember -> {
                cityMemberRepository.delete(cityMember);
                return ResponseEntity.ok(message("退出city成功"));
            })
            .orElseGet(()-> ResponseEntity.ok(message("未找到")));
    }

    /**
     * admin修改city
     */
    @PostMapping("/ms-city/admin/update-city/city/{cityid}")
    @Timed
    public ResponseEntity<?> updateCity(@PathVariable Long cityid, @Valid @RequestBody CityVM cityVM) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        CityVM newdata = msAuthority.cityVMSQLInject(cityVM);
        City city = cityRepository.findOne(cityid);
        city.setType(newdata.getType());
        city.setRemark(newdata.getRemark());
        city.setDescription(newdata.getDescription());
        city.setAvatar(newdata.getAvatar());
        cityRepository.save(city);
        return ResponseEntity.ok(message("city修改成功"));
    }

    /**
     * 给报名成功的所有个人发短信
     */
    @PostMapping("/ms-city/send-message/success")
    @Timed
    public ResponseEntity<?> sendSuccessMessage() throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(ResponseMessage.message("权限不足"));
        }
        String phones = cityMemberRepository.findAll()
            .stream()
            .map(cityMember -> {
                return cityMember.getUser().getFullPhone();
            })
            .reduce((v, u) -> v + "," + u).get();
        SendMessageUtil.sendEnrollOKMessage(phones);
        return ResponseEntity.ok(message("发送成功"));
    }

    /**
     * 判断是否报名个人赛
     */
    @PostMapping("/ms-city/check-city-member")
    @Timed
    public ResponseEntity<?> checkCityMember() throws URISyntaxException {
        if (msAuthority.isAnonymous()) {
            return ResponseEntity.ok(message("请登录"));
        }
        return cityMemberRepository.findOneByUser(msAuthority.current())
            .map(cityMember -> (ResponseEntity.ok(cityMember)))
            .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
