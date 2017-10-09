package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.config.HomeConfig;
import com.bisaibang.monojwt.domain.game.Card;
import com.bisaibang.monojwt.domain.game.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lynn on 2017/3/23.
 */
public interface HomeConfigRepository extends JpaRepository<HomeConfig, Long> {
}
