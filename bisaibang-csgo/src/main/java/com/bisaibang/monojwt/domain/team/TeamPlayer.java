package com.bisaibang.monojwt.domain.team;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.vm.TeamPlayerVM;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TeamPlayer.
 */
@Entity
@Table(name = "team_player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeamPlayer implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team team;

    @Column(name = "state")
    private String state;

    @Column(name = "enter_time")
    private ZonedDateTime enterTime;

    @Column(name = "sub_state")
    private String subState;

    @Column(name = "name")
    private String name;

    @Column(name = "personal_id")
    private String personalId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mail")
    private String mail;

    @Column(name = "nick_name")
    private String nickName;


    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        this.subState = subState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ZonedDateTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(ZonedDateTime enterTime) {
        this.enterTime = enterTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeamPlayer teamPlayer = (TeamPlayer) o;
        if(teamPlayer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, teamPlayer.id);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TeamPlayer{" +
            "id=" + id +
            ", team=" + team +
            ", state='" + state + '\'' +
            ", enterTime=" + enterTime +
            ", subState='" + subState + '\'' +
            ", name='" + name + '\'' +
            ", personalId='" + personalId + '\'' +
            ", phone='" + phone + '\'' +
            ", mail='" + mail + '\'' +
            ", nickName='" + nickName + '\'' +
            '}';
    }

    private TeamPlayer(){
        this.setEnterTime(ZonedDateTime.now());
    }

    public static TeamPlayer create(Team t, TeamPlayer data){
        TeamPlayer tP = new TeamPlayer();
        tP.setTeam(t);
        tP.setState("member");
        tP.setName(data.getName());
        tP.setPersonalId(data.getPersonalId());
        tP.setPhone(data.getPhone());
        tP.setMail(data.getMail());
        tP.setNickName(data.getNickName());
        return tP;
    }

    public static TeamPlayer createCaptain(Team t, User p){
        TeamPlayer tP = new TeamPlayer();
        tP.setTeam(t);
        tP.setState("captain");
        tP.setName(p.getFirstName());
        tP.setPersonalId(p.getPersonalID());
        tP.setPhone(p.getPhone());
        tP.setMail(p.getEmail());
        tP.setNickName(p.getNickName());
        return tP;
    }

    public static TeamPlayer create(Team t, TeamPlayerVM data){
        TeamPlayer tP = new TeamPlayer();
        tP.setTeam(t);
        tP.setState("member");
        tP.setName(data.getName());
        tP.setPersonalId(data.getPersonalId());
        tP.setPhone(data.getPhone());
        tP.setMail(data.getMail());
        tP.setNickName(data.getNickName());
        return tP;
    }

}
