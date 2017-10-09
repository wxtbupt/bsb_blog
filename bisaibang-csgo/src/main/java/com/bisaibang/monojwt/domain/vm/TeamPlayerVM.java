package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.team.TeamPlayer;

import java.util.List;

/**
 * Created by liujinduo on 2016/10/26.
 */
public class TeamPlayerVM {

    private String name;

    private String personalId;

    private String phone;

    private String mail;

    private String nickName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personal_id) {
        this.personalId = personal_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nick_name) {
        this.nickName = nick_name;
    }

    @Override
    public String toString() {
        return "TeamPlayerVM{" +
            "name='" + name + '\'' +
            ", personalId='" + personalId + '\'' +
            ", phone='" + phone + '\'' +
            ", mail='" + mail + '\'' +
            ", nickName='" + nickName + '\'' +
            '}';
    }
}
