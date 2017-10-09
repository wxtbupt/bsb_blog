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
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findOneById(Long id);
    Page<Team> findAllByCaptainAndTypeOrderByIdDesc(User user, String type, Pageable pageable);
    Optional<Team> findOneByName(String teamname);
    Page<Team> findAll(Pageable pageable);
    Optional<Team> findOneByCaptain(User user);
}
