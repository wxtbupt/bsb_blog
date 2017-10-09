package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.team.TeamPlayer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the TeamPlayer entity.
 */
@SuppressWarnings("unused")
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {

    List<TeamPlayer> findAllByTeamAndState(Team team, String state);

    List<TeamPlayer> findAllByNickName(String nickName);

    List<TeamPlayer> findAllByTeam(Team team);

}
