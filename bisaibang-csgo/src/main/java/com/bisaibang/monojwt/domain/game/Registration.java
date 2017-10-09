package com.bisaibang.monojwt.domain.game;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.team.Team;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Registration.
 */
@Entity
@Table(name = "registration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Registration implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "group_id")
    private Long groupId;

    @ManyToOne
    private Team team;

    @Column(name = "team_name")
    private String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Registration registration = (Registration) o;
        if (registration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, registration.id);
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", groupId=" + groupId +
                ", team=" + team +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Registration create(Team team){
        Registration r = new Registration();
        r.team = team;
        r.teamName = team.getName();
        return r;
    }

    private Registration() {
        type = "normal";
        state = "normal";
    }


}
