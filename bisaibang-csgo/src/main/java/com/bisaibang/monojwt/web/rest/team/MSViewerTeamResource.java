package com.bisaibang.monojwt.web.rest.team;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.vm.TeamInfoVM;
import com.bisaibang.monojwt.repository.TeamPlayerRepository;
import com.bisaibang.monojwt.repository.TeamRepository;
import com.bisaibang.monojwt.repository.UserRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.team.MSTeamService;
import com.bisaibang.monojwt.service.util.SendMessageUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import java.net.URISyntaxException;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by liujinduo on 2016/10/26.
 */
@RestController
@RequestMapping("/api")
public class MSViewerTeamResource {

    private final Logger log = LoggerFactory.getLogger(MSViewerTeamResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MSTeamService msTeamService;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    /**
     * 获取所有小队玩家
     * by viewer
     */
    @RequestMapping(value = "/ms-team/viewer/get-all/{teamid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAllPlayer(@PathVariable Long teamid) throws URISyntaxException {
        return msTeamService.getAllPlayer(teamid);
    }

    /**
     * 获取一个team by teamid
     * by viewer
     */
    @RequestMapping(value = "/ms-team/viewer/get-team/{teamid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getTeamById(@PathVariable Long teamid) throws URISyntaxException {
        return teamRepository.findOneById(teamid)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
