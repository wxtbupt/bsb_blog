package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.forum.Post;
import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.team.TeamPlayer;
import org.springframework.data.domain.Page;

/**
 * Created by Lynn on 2017/3/22.
 */
public class UserVM {

    private String email;

    private String personalId;

    private String name;

    private String nickname;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserVM{" +
            "email='" + email + '\'' +
            ", personalId='" + personalId + '\'' +
            ", name='" + name + '\'' +
            ", nickname='" + nickname + '\'' +
            '}';
    }

    public static UserVM create(UserVM data){
        UserVM uVM = new UserVM();
        uVM.setName(data.getName());
        uVM.setEmail(data.getEmail());
        uVM.setPersonalId(data.getPersonalId());
        uVM.setNickname(data.getNickname());
        return uVM;
    }
}
