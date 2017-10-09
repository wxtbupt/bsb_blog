package com.bisaibang.monojwt.domain.forum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "forum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Forum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "state")
    private String state;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Forum forum = (Forum) o;
        if (forum.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, forum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Forum create(String name) {
        Forum forum = new Forum();
        forum.setState("normal");
        forum.setType("normal");
        forum.setName(name);
        return forum;
    }

    @Override
    public String toString() {
        return "Forum{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", state='" + state + '\'' +
            '}';
    }

    public void deleteForum(){
        this.type = "delete";
    }

}
