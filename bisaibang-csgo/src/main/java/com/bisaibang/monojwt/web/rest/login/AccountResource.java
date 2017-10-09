package com.bisaibang.monojwt.web.rest.login;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.util.NotifyContent;
import com.bisaibang.monojwt.repository.SmsCodeRepository;
import com.bisaibang.monojwt.repository.UserRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.security.SecurityUtils;
import com.bisaibang.monojwt.service.dto.UserDTO;
import com.bisaibang.monojwt.service.generate.MailService;
import com.bisaibang.monojwt.service.generate.SmsCodeService;
import com.bisaibang.monojwt.service.generate.UserService;
import com.bisaibang.monojwt.service.util.RandomUtil;
import com.bisaibang.monojwt.service.util.SendMessageUtil;
import com.bisaibang.monojwt.web.rest.generate.util.HeaderUtil;
import com.bisaibang.monojwt.web.rest.generate.vm.KeyAndPasswordVM;
import com.bisaibang.monojwt.web.rest.generate.vm.ManagedUserVM;
import com.bisaibang.monojwt.web.rest.generate.vm.ResetPasswordVM;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MSAuthority msAuthority;
    //bsb v2
    @Autowired
    private SmsCodeRepository smsCodeRepository;

    @Autowired
    private SmsCodeService smsCodeService;

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or e-mail is already in use
     */
    @PostMapping(path = "/register",
                    produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        return userRepository.findOneByLogin(managedUserVM.getLogin())
                .map(user -> ResponseEntity.ok(message("login already in use")))
                .orElseGet(() -> userRepository.findOneByNickName(managedUserVM.getNickName())
                        .map(user -> ResponseEntity.ok(message("nickname already in use")))
                        .orElseGet(() -> userRepository.findOneByPhone(managedUserVM.getPhone())
                                    .map(user -> ResponseEntity.ok(message("phone already in use")))
                                    .orElseGet(()->{
                                        if (smsCodeService.contrastCode(managedUserVM.getConfirmCode(), smsCodeService.findPhone_Code(managedUserVM.getPhone()))) {
                                            User user = userService.createUserInformation(managedUserVM.getLogin(), managedUserVM.getPassword(),
                                                    managedUserVM.getFirstName(), managedUserVM.getLastName(), managedUserVM.getEmail().toLowerCase(),
                                                    managedUserVM.getNickName(),managedUserVM.getPhone(), managedUserVM.getConfirmCode(), managedUserVM.getLangKey());
                                            return ResponseEntity.ok(message("创建成功"));
                                        } else {
                                            return ResponseEntity.ok(message("验证码错误"));
                                        }
                                    })
                        )
                );
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return userService.activateRegistration(key)
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/account/current")
    @Timed
    public ResponseEntity<?> getAccountCurrent() {
        return ResponseEntity.ok(msAuthority.current());
    }

    @GetMapping("/account/current-phone")
    @Timed
    public ResponseEntity<?> getAccountCurrentPhone() {
        return ResponseEntity.ok(msAuthority.current().getFullPhone());
    }


    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @PostMapping("/account")
    @Timed
    public ResponseEntity<String> saveAccount(@Valid @RequestBody UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }
        return userRepository
            .findOneByLogin(SecurityUtils.getCurrentUserLogin())
            .map(u -> {
                userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getLangKey());
                return new ResponseEntity<String>(HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = "/account/change_password",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST   /account/reset_password/init : Send an e-mail to reset the password of the user
     *
     * @param mail the mail of the user
     * @return the ResponseEntity with status 200 (OK) if the e-mail was sent, or status 400 (Bad Request) if the e-mail address is not registered
     */
    @PostMapping(path = "/account/reset_password/init",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail) {
        return userService.requestPasswordReset(mail)
            .map(user -> {
                mailService.sendPasswordResetMail(user);
                return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
            }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    /**
     * POST   /account/reset_password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset_password/finish",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
              .map(user -> new ResponseEntity<String>(HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH);
    }

    /**
     * 发送验证码
     */
    @RequestMapping(value = "/register/send_sms_code",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> sendSmsCode(@RequestBody String phone) {
        return userRepository.findOneByPhone(phone)
            .map(user -> ResponseEntity.ok(message(NotifyContent.USER_REGISTERED)))
            .orElseGet(() -> smsCodeService.findPhoneExist(phone)
                .map(exist -> {
                        if (smsCodeService.findDateLess60(phone, ZonedDateTime.now())) {
                            return ResponseEntity.ok(message(NotifyContent.FLUENT_REGISTERED));
                        } else {
                            String code = RandomUtil.get6SMSCode();
                            smsCodeService.updateSmsCode(phone, code);
                            return SendMessageUtil.sendCode(phone, code)
                                .map(success -> ResponseEntity.ok(message(NotifyContent.OPERATION_SUCCESS)))
                                .orElseGet(() -> ResponseEntity.ok(message(NotifyContent.ALIDAYU_PHONE_ERROR)));
                        }
                    }
                )
                .orElseGet(() -> {
                        String code = RandomUtil.get6SMSCode();
                        smsCodeService.createSmsCode(phone, code);
                        return SendMessageUtil.sendCode(phone, code)
                            .map(success -> ResponseEntity.ok(message(NotifyContent.OPERATION_SUCCESS)))
                            .orElseGet(() -> ResponseEntity.ok(message(NotifyContent.ALIDAYU_PHONE_ERROR)));
                    }
                )
            );
    }

    /**
     * 忘记密码界面的获取验证码部分
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/forget/send_sms_code",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> forgetSendSmsCode(@RequestBody String phone) {
        return userRepository.findOneByPhone(phone)
            .map(user ->
                smsCodeService.findPhoneExist(phone)
                    .map(exist -> {
                        String code = RandomUtil.get6SMSCode();
                        //String code = "003344";
                        smsCodeService.updateSmsCode(phone, code);
                        if (SendMessageUtil.sendForgetCode(phone, code)) {
                            return ResponseEntity.ok(message("发送成功"));
                        } else {
                            return ResponseEntity.ok(message(NotifyContent.ALIDAYU_PHONE_ERROR));
                        }
                    })
                    .orElseGet(() -> new ResponseEntity<>(message("无号码"), HttpStatus.NOT_FOUND)))
            .orElseGet(() -> new ResponseEntity<>(message("无用户"), HttpStatus.NOT_FOUND));
    }

    /**
     * 忘记密码界面的修改密码部分
     */
    @RequestMapping(value = "/forget/resetPassword",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordVM resetPasswordVM) {
        String phone = resetPasswordVM.getPhone();
        String code = resetPasswordVM.getCode();
        String newPassword = resetPasswordVM.getNewPassword();
        return smsCodeRepository.findOneByPhoneAndCode(phone, code)
            .map(smsCode -> {
                smsCodeService.changePhoneCode(phone);
                return userRepository.findOneByLogin(phone)
                    .map(user -> {
                        user.setPassword(passwordEncoder.encode(newPassword));
                        userRepository.save(user);
                        return ResponseEntity.ok(message("成功修改"));
                    })
                    .orElseGet(() -> new ResponseEntity<>(message("无用户"), HttpStatus.NOT_FOUND));
            })
            .orElseGet(() -> new ResponseEntity<>(message("无号码"), HttpStatus.NOT_FOUND));
    }


}
