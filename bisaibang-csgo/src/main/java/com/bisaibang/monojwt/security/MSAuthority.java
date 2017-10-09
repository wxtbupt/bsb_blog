package com.bisaibang.monojwt.security;


import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.domain.article.Comment;
import com.bisaibang.monojwt.domain.config.HomeConfig;
import com.bisaibang.monojwt.domain.forum.Forum;
import com.bisaibang.monojwt.domain.team.City;
import com.bisaibang.monojwt.domain.team.TeamPlayer;
import com.bisaibang.monojwt.domain.video.VideoBroadcast;
import com.bisaibang.monojwt.domain.forum.Post;
import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.game.Registration;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.vm.CityVM;
import com.bisaibang.monojwt.domain.vm.TeamCreateVM;
import com.bisaibang.monojwt.domain.vm.TeamPlayerVM;
import com.bisaibang.monojwt.domain.vm.UserVM;
import com.bisaibang.monojwt.repository.*;
import net.logstash.logback.encoder.org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class MSAuthority {

    private final Logger log = LoggerFactory.getLogger(MSAuthority.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;


    // 创建个人信息 无需 REST


    public Boolean isAdmin() {
        return currentUserId().equals(Integer.toUnsignedLong(3));
    }

    public Boolean isBeyond(String phone) {
        return !(current().Reliable() && userRepository.findOneByPhone(phone).get().Reliable());
    }

    public Boolean isBeyondCurrent() {
        return !(current().Reliable());
    }


    public User current() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin())
            .map(user -> user)
            .orElseGet(() -> User.create(2));
    }

    public Long currentUserId() {
        return current().getId();
    }

    public Boolean isAnonymous() {
        return currentUserId().equals(Integer.toUnsignedLong(2));
    }


    /**
     * 对Article进行注入检测
     */
    public Article articleSQLInject(Article a) {
        a.setName(StringEscapeUtils.escapeSql(a.getName()));
        a.setContent(StringEscapeUtils.escapeSql(a.getContent()));
        a.setState(StringEscapeUtils.escapeSql(a.getState()));
        a.setTitle(StringEscapeUtils.escapeSql(a.getTitle()));
        return a;
    }


    public Comment commentSQLInject(Comment c) {
        c.setContent(StringEscapeUtils.escapeSql(c.getContent()));
        c.setTitle(StringEscapeUtils.escapeSql(c.getTitle()));
        c.setType(StringEscapeUtils.escapeSql(c.getType()));
        c.setState(StringEscapeUtils.escapeSql(c.getState()));
        c.setLevel(StringEscapeUtils.escapeSql(c.getLevel()));
        return c;
    }

    /**
     * 对Post进行注入检测
     */
    public Post postSQLInject(Post data) {
        data.setAuthorName(StringEscapeUtils.escapeSql(data.getAuthorName()));
        data.setContent(StringEscapeUtils.escapeSql(data.getContent()));
        data.setState(StringEscapeUtils.escapeSql(data.getState()));
        data.setTitle(StringEscapeUtils.escapeSql(data.getTitle()));
        return data;
    }

    /**
     * 对SingleThread进行注入检测
     */
    public SingleThread singleThreadSQLInject(SingleThread thread) {
        thread.setAuthorName(StringEscapeUtils.escapeSql(thread.getAuthorName()));
        thread.setName(StringEscapeUtils.escapeSql(thread.getName()));
        thread.setTitle(StringEscapeUtils.escapeSql(thread.getTitle()));
        thread.setContent(StringEscapeUtils.escapeSql(thread.getContent()));
        thread.setState(StringEscapeUtils.escapeSql(thread.getState()));
        thread.setSection(StringEscapeUtils.escapeSql(thread.getSection()));
        thread.setLatestCommentUser(StringEscapeUtils.escapeSql(thread.getLatestCommentUser()));
        return thread;
    }

    /**
     * 判断当前用户是否为某个team的队长
     */
    public Boolean isCaptain(Long teamId) {
        Team t = teamRepository.findOne(teamId);
        return t.getCaptain().getId().equals(currentUserId());
    }

    public Boolean isCreator(Long articleid) {
        return articleRepository.findOne(articleid).getCreator().getId().equals(currentUserId());
    }

    /**
     * 对Forum进行注入检测
     */
    public Forum forumSQLInject(Forum forum) {
        forum.setName(StringEscapeUtils.escapeSql(forum.getName()));
        forum.setState(StringEscapeUtils.escapeSql(forum.getState()));
        return forum;
    }

    public VideoBroadcast VBSQLInject(VideoBroadcast vb) {
        vb.setName(StringEscapeUtils.escapeSql(vb.getName()));
        vb.setState(StringEscapeUtils.escapeSql(vb.getState()));
        vb.setUrl(StringEscapeUtils.escapeSql(vb.getUrl()));
        vb.setTag(StringEscapeUtils.escapeSql(vb.getTag()));
        vb.setIntroduction(StringEscapeUtils.escapeSql((vb.getIntroduction())));
        return vb;
    }


    /**
     * 对TCVM进行注入检测
     */
    public TeamCreateVM TCVMSQLInject(TeamCreateVM TCVM) {
        TCVM.setDescription(StringEscapeUtils.escapeSql(TCVM.getDescription()));
        TCVM.setName(StringEscapeUtils.escapeSql(TCVM.getName()));
        TCVM.setRemark(StringEscapeUtils.escapeSql(TCVM.getRemark()));
        TCVM.setTeamAvatar(StringEscapeUtils.escapeSql(TCVM.getTeamAvatar()));
        return TCVM;
    }

    /**
     * 对User进行注入检测
     */
    public User userSQLInject(User user) {
        user.setAvatarUrl(StringEscapeUtils.escapeSql(user.getAvatarUrl()));
        user.setActivationKey(StringEscapeUtils.escapeSql(user.getActivationKey()));
        user.setConfig(StringEscapeUtils.escapeSql(user.getConfig()));
        user.setEmail(StringEscapeUtils.escapeSql(user.getEmail()));
        user.setPersonalEmail(StringEscapeUtils.escapeSql(user.getPersonalEmail()));
        user.setConfirmCode(StringEscapeUtils.escapeSql(user.getConfirmCode()));
        user.setFirstName(StringEscapeUtils.escapeSql(user.getFirstName()));
        user.setLangKey(StringEscapeUtils.escapeSql(user.getLangKey()));
        user.setLastName(StringEscapeUtils.escapeSql(user.getLastName()));
        user.setNickName(StringEscapeUtils.escapeSql(user.getNickName()));
        user.setLogin(StringEscapeUtils.escapeSql(user.getLogin()));
        user.setPhone(StringEscapeUtils.escapeSql(user.getPhone()));
        user.setPersonalID(StringEscapeUtils.escapeSql(user.getPersonalID()));
        user.setResetKey(StringEscapeUtils.escapeSql(user.getResetKey()));
        return user;
    }


    public Boolean isNickNameExist(String battleid) {
        return userRepository.findOneByNickName(battleid).isPresent();
    }

    public Boolean isNumberEnough(Long teamId) {
        Team t = teamRepository.findOne(teamId);
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findAllByTeam(t);
        return teamPlayers.size() >= 6;
    }

    /**
     * 判断当前用户是否为某个team的队长或者admin
     */
    public Boolean isTeamManager(Long teamId) {
        return isAdmin()||isCaptain(teamId);
    }


    /**
     * 对homeConfig进行注入检测
     */
    public HomeConfig homeConfigSQLInject(HomeConfig homeConfig) {
        homeConfig.setConfig(StringEscapeUtils.escapeSql(homeConfig.getConfig()));
        homeConfig.setArticle_config(StringEscapeUtils.escapeSql(homeConfig.getArticle_config()));
        homeConfig.setVideo_config(StringEscapeUtils.escapeSql(homeConfig.getVideo_config()));
        homeConfig.setState(StringEscapeUtils.escapeSql(homeConfig.getState()));
        homeConfig.setType(StringEscapeUtils.escapeSql(homeConfig.getType()));
        return homeConfig;
    }



    public UserVM userVMSQLInject(UserVM userVM) {
        userVM.setNickname(StringEscapeUtils.escapeSql(userVM.getNickname()));
        userVM.setName(StringEscapeUtils.escapeSql(userVM.getName()));
        userVM.setPersonalId(StringEscapeUtils.escapeSql(userVM.getPersonalId()));
        userVM.setEmail(StringEscapeUtils.escapeSql(userVM.getEmail()));
        return userVM;
    }


    public CityVM cityVMSQLInject(CityVM cityVM) {
        cityVM.setAvatar(StringEscapeUtils.escapeSql(cityVM.getAvatar()));
        cityVM.setDescription(StringEscapeUtils.escapeSql(cityVM.getDescription()));
        cityVM.setRemark(StringEscapeUtils.escapeSql(cityVM.getRemark()));
        cityVM.setType(StringEscapeUtils.escapeSql(cityVM.getType()));
        return cityVM;
    }

    /**
     * TeamplayerVM注入检测
     */
    public TeamPlayerVM TPVMSQLInject(TeamPlayerVM teamPlayerVM) {
        teamPlayerVM.setName(StringEscapeUtils.escapeSql(teamPlayerVM.getName()));
        teamPlayerVM.setMail(StringEscapeUtils.escapeSql(teamPlayerVM.getMail()));
        teamPlayerVM.setPhone(StringEscapeUtils.escapeSql(teamPlayerVM.getPhone()));
        teamPlayerVM.setPersonalId(StringEscapeUtils.escapeSql(teamPlayerVM.getPersonalId()));
        teamPlayerVM.setNickName(StringEscapeUtils.escapeSql(teamPlayerVM.getNickName()));
        return teamPlayerVM;
    }


}
