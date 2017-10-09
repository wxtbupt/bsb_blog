package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.City;
import com.bisaibang.monojwt.domain.team.CityMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

/**
 * Created by Lynn on 2017/3/23.
 */
public interface CityMemberRepository extends JpaRepository<CityMember, Long> {
    Page<CityMember> findAllByCity(City city, Pageable pageable);
    Optional<CityMember> findOneByUser(User user);
    Optional<CityMember> findOneByCityAndUser(City city, User user);
}
