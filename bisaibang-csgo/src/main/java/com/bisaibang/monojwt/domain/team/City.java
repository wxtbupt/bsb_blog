package com.bisaibang.monojwt.domain.team;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.vm.TeamCreateVM;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "description")
    private String description;

    @Column(name = "avatar")
    private String avatar;


    @Column(name = "create_date")
    private ZonedDateTime createDate;

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

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City team = (City) o;
        if (team.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private City() {
        createDate = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        return "City{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", avatar='" + avatar + '\'' +
            ", type='" + type + '\'' +
            ", remark='" + remark + '\'' +
            '}';
    }
}
