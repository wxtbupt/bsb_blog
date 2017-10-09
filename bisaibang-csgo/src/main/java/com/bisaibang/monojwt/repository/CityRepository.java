package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.team.City;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lynn on 2017/3/23.
 */
public interface CityRepository extends JpaRepository<City, Long> {
    Page<City> findAll(Pageable pageable);
}
