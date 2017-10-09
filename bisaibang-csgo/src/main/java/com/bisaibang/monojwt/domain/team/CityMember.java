package com.bisaibang.monojwt.domain.team;

import com.bisaibang.monojwt.domain.people.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A city_member.
 */
@Entity
@Table(name = "city_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CityMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private City city;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "enter_time")
    private ZonedDateTime enterTime;

    @Override
    public String toString() {
        return "CityMember{" +
                "id=" + id +
                ", user=" + user +
                ", city=" + city +
                ", nickName='" + nickName + '\'' +
                ", enterTime=" + enterTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public ZonedDateTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(ZonedDateTime enterTime) {
        this.enterTime = enterTime;
    }

    private CityMember(){
        this.setEnterTime(ZonedDateTime.now());
    }

    public static CityMember create(City city, User user){
        CityMember cityMember = new CityMember();
        cityMember.setUser(user);
        cityMember.setNickName(user.getNickName());
        cityMember.setCity(city);
        return cityMember;
    }


}
