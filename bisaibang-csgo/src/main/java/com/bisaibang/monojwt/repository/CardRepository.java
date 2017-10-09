package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.game.Card;
import com.bisaibang.monojwt.domain.game.Registration;
import com.bisaibang.monojwt.domain.people.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Lynn on 2017/3/23.
 */
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByRegistration(Registration registration);
}
