package com.bisaibang.monojwt.domain.config;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "home_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HomeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config")
    private String config;

    @Column(name = "type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "article_config")
    private String article_config;

    @Column(name = "video_config")
    private String video_config;

    public String getVideo_config() {
        return video_config;
    }

    public void setVideo_config(String video_config) {
        this.video_config = video_config;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
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

    public String getArticle_config() {
        return article_config;
    }

    public void setArticle_config(String article_config) {
        this.article_config = article_config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HomeConfig forum = (HomeConfig) o;
        if (forum.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, forum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private HomeConfig() {
    }


}
