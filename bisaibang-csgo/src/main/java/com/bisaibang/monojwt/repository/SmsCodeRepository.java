package com.bisaibang.monojwt.repository;


import com.bisaibang.monojwt.domain.util.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created by szzz on 2016/7/6.
 * bsb v2
 */
public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    Optional<SmsCode> findOneByPhone(String phone);

    @Query("select smscode from SmsCode smscode where smscode.phone = ?1 and smscode.code = ?2")
    Optional<SmsCode> findOneByPhoneAndCode(String phone, String code);

}
