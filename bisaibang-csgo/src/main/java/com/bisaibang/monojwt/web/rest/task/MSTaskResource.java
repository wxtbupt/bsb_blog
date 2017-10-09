package com.bisaibang.monojwt.web.rest.task;

import com.bisaibang.monojwt.domain.chat.ChatLog;
import com.bisaibang.monojwt.domain.config.HomeConfig;
import com.bisaibang.monojwt.domain.game.Registration;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.vm.DataVM;
import com.bisaibang.monojwt.repository.*;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.forum.MSPostService;
import com.bisaibang.monojwt.service.util.HttpRequestUtils;
import com.bisaibang.monojwt.service.util.QiniuUtil;
import com.bisaibang.monojwt.service.util.SendCloud.SendCloudMailService;
import com.bisaibang.monojwt.web.rest.chat.ChatController;
import com.codahale.metrics.annotation.Timed;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by ccy on 2017/2/5.
 */
@RestController
@RequestMapping("/api")
public class MSTaskResource {
    private final Logger log = LoggerFactory.getLogger(MSTaskResource.class);

    @Autowired
    private SingleThreadRepository threadRepository;

    @Autowired
    private RegistrationRepository rRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSPostService msPostService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository ArticleRepository;

    @Autowired
    private HomeConfigRepository homeConfigRepository;

    @Autowired
    private ChatController chatController;


    //发邮件
    @RequestMapping(value = "/ms-task/e-mail",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createPost(@RequestBody @Valid String toAddr) throws Throwable, URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }

        SendCloudMailService.send_common(toAddr);

        return ResponseEntity.ok(message("123"));
    }


    //更新用户头像
    @RequestMapping(value = "/ms-task/update-avatar-url",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateAvatarUrl(@Valid @RequestBody String key) throws URISyntaxException {
        User current = msAuthority.current();
        QiniuUtil imgupload = new QiniuUtil();
        imgupload.setBucketName("minisite-one");
        if (current.getAvatarUrl() != null && current.getAvatarUrl().length() > 33) {
            String oldKey = current.getAvatarUrl().substring(33);//33 is the length of https://msone.bisaibang.com/
            imgupload.setKey(oldKey);
            try {
                imgupload.delete();
            }catch (Exception e){
                //old key doesnt exist, dev mode?
            }
        }
        current.setAvatarUrl(key);

        userRepository.save(current);

        return ResponseEntity.ok(message("更改头像成功"));
    }


    @RequestMapping(value = "/ms-task/blizzard/callback",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String blizzardCallback(@RequestParam("code") String code, @RequestParam("state") Long userId) throws URISyntaxException {
        //log.debug("中文code:" + code);
        List<NameValuePair> requestPayload = new ArrayList<NameValuePair>(3);
        requestPayload.add(new BasicNameValuePair("redirect_uri", "https://owod.allied-esports.cn/api/ms-task/blizzard/callback"));
        requestPayload.add(new BasicNameValuePair("grant_type", "authorization_code"));
        requestPayload.add(new BasicNameValuePair("code", code));
        String token = null;
        String battletag = null;
        for (int i = 0; i < 3 && token == null;i++) {
            JSONObject accessResult = HttpRequestUtils.httpPost(
                "https://www.battlenet.com.cn/oauth/token",
                requestPayload,
                "Basic YmFubWNodjJ1Y253enZzdDY0Zmo2aGhiajk2eHFueno6elBBYmN3YVRIVkt0azhySmdaQTVZVllHRFlEelczQnA="
            );
            try {
                token = accessResult.getString("access_token");
                //log.debug("中文token:" + token);
            } catch (Exception e) {
                token = null;
            }
        }
        if (token == null){
            return "redirect:/";//TODO 返回超时信息
        }
        for (int i = 0; i < 3 && battletag == null;i++) {
            JSONObject result = HttpRequestUtils.httpGet("https://api.battlenet.com.cn/account/user?access_token=" + token);
            try {
                battletag = result.getString("battletag");
                //log.debug("中文battletag:" + battletag);
            } catch (Exception e) {
                battletag = null;
            }
        }
        if (battletag == null){
            return "redirect:/";//TODO 返回超时信息
        }
        User user = userRepository.findOne(userId);
        user.setNickName(battletag);
        userRepository.save(user);

        return "redirect:/personInfo";
    }

    //admin data
    @GetMapping("/ms-task/data")
    @Timed
    public ResponseEntity<?> adminDataChech() throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        List<User> users = userRepository.findAll();
        List<Registration> registrations = rRepository.findAll();
        Long nickNameNumber = users
                .stream()
                .filter(User::hasNickName)
                .count();



        return ResponseEntity.ok(DataVM.create(users.size(), nickNameNumber, registrations.size()));
    }


    @PostMapping("/ms-task/remove")
    @Timed
    public ResponseEntity<?> adminRemoveBattleTag(@RequestBody String phone) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        User user = userRepository.findOneByPhone(phone).get();
        user.removeNickName();
        userRepository.save(user);
        return ResponseEntity.ok(message("越权"));
    }

    @PostMapping("/ms-task/update-home-page/article")
    @Timed
    public ResponseEntity<?> adminUpdateHomeArticle(@RequestBody HomeConfig data) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        HomeConfig homePage = homeConfigRepository.findOne(1L);
        data = msAuthority.homeConfigSQLInject(data);
        homePage.setArticle_config(data.getArticle_config());
        homeConfigRepository.save(homePage);
        return ResponseEntity.ok(message("成功"));
    }

    @PostMapping("/ms-task/update-home-page/video")
    @Timed
    public ResponseEntity<?> adminUpdateHomeVideo(@RequestBody HomeConfig data) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        HomeConfig homePage = homeConfigRepository.findOne(1L);
        data = msAuthority.homeConfigSQLInject(data);
        homePage.setVideo_config(data.getVideo_config());
        homeConfigRepository.save(homePage);
        return ResponseEntity.ok(message("成功"));
    }

    @GetMapping("/ms-task/get-home-page")
    @Timed
    public ResponseEntity<?> adminGetHome() throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        return ResponseEntity.ok(homeConfigRepository.findOne(1L));
    }

    @PostMapping("/ms-task/test-Full-chat/{id}")
    @Timed
    public ResponseEntity<?> testFullChat(@PathVariable Long id, @RequestBody String content) throws Exception {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("越权"));
        }
        chatController.sendAll(content,id);
        return ResponseEntity.ok("成功");
    }



}
