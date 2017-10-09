package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.game.Registration;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Registration entity.
 */
@SuppressWarnings("unused")
public interface RegistrationRepository extends JpaRepository<Registration,Long> {
    Registration findOne(Long id);

    Optional<Registration> findOneByTeam(Team team);

    Optional<Registration> findOneByTeamName(String teamName);
    List<Registration> findAllByGroupId(Long id);
    List<Registration> findAll();
}
