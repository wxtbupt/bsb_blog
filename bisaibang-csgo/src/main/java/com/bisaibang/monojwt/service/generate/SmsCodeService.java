package com.bisaibang.monojwt.service.generate;

import com.bisaibang.monojwt.domain.util.SmsCode;
import com.bisaibang.monojwt.repository.SmsCodeRepository;
import com.bisaibang.monojwt.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Created by szzz on 2016/7/9.
 */

@Service
@Transactional
public class SmsCodeService {

    private final Logger log = LoggerFactory.getLogger(SmsCodeService.class);

    @Autowired
    private SmsCodeRepository smsCodeRepository;

    public SmsCode createSmsCode(String phone, String code) {
        SmsCode newSmsCode = new SmsCode();
        newSmsCode.setPhone(phone);
        newSmsCode.setCode(code);
        newSmsCode.setCreatedDate(ZonedDateTime.now());
        smsCodeRepository.save(newSmsCode);
        return newSmsCode;
    }

    public void updateSmsCode(String phone, String code) {
        smsCodeRepository.findOneByPhone(phone).ifPresent(u -> {
            u.setCode(code);
            u.setCreatedDate(ZonedDateTime.now());;
            smsCodeRepository.save(u);
        });
    }

    public Optional<SmsCode> findPhoneExist(String phone) {
        return smsCodeRepository.findOneByPhone(phone);
    }

    public Boolean findDateLess60(String phone, ZonedDateTime Date) {
        Optional<SmsCode> existedSmsCode = smsCodeRepository.findOneByPhone(phone);
        ZonedDateTime time = existedSmsCode.get().getCreatedDate();
        time = time.plusSeconds(60);
        return Date.isBefore(time);

    }

    public  boolean contrastCode(String currentCode, String baseCode){
        return currentCode.equals(baseCode);
    }

    public String findPhone_Code(String phone)
    {
        Optional<SmsCode> existingSmsCode = smsCodeRepository.findOneByPhone(phone);
        String baseCode = existingSmsCode.get().getCode();
        return baseCode;
    }

    public void changePhoneCode(String phone) {
        smsCodeRepository.findOneByPhone(phone)
            .ifPresent(s -> {
                s.setCode(RandomUtil.get6SMSCode());
                smsCodeRepository.save(s);
            });
    }
}
