package com.bisaibang.monojwt.domain.team;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.vm.TeamCreateVM;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "description")
    private String description;

    @Column(name = "team_avatar")
    private String teamAvatar;

    @ManyToOne
    private User captain;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "join_state")
    private String joinState;

    @Column(name = "type")
    private String type;

    @Column(name = "remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeamAvatar() {
        return teamAvatar;
    }

    public void setTeamAvatar(String teamAvatar) {
        this.teamAvatar = teamAvatar;
    }

    public User getCaptain() {
        return captain;
    }

    public void setCaptain(User captain) {
        this.captain = captain;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getJoinState() {
        return joinState;
    }

    public void setJoinState(String joinState) {
        this.joinState = joinState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Team empty() {
        Team t = new Team();
        t.setId(Integer.toUnsignedLong(1));
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", teamAvatar='" + teamAvatar + '\'' +
                ", captain=" + captain +
                ", createDate=" + createDate +
                ", joinState='" + joinState + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    private Team() {
        createDate = ZonedDateTime.now();
        joinState = "true";
    }

    public static Team create(String name, User user, String descrip, String avatar) {
        Team t = new Team();
        t.setName(name);
        t.setCaptain(user);
        t.setDescription(descrip);
        t.setTeamAvatar(avatar);
        return t;
    }

    public static Team create(String name, User user, String descrip, String avatar, String remark) {
        Team t = new Team();
        t.setName(name);
        t.setCaptain(user);
        t.setDescription(descrip);
        t.setTeamAvatar(avatar);
        t.setRemark(remark);
        return t;
    }



    public static Team create(TeamCreateVM tCVM, User cap, String remark) {
        return create(tCVM.getName(), cap, tCVM.getDescription(), tCVM.getTeamAvatar(), remark);
    }

    public static Team create(TeamCreateVM tCVM, User cap) {
        return create(tCVM.getName(), cap, tCVM.getDescription(), tCVM.getTeamAvatar(), tCVM.getRemark());
    }

    public static Team create(Long id) {
        Team t = new Team();
        t.setId(id);
        return t;
    }
}
