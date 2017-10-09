
package com.bisaibang.monojwt.domain.video;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "video_broadcast")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VideoBroadcast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "tag")
    private String tag;

    @Column(name = "comment_number")
    private Long commentNumber;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "is_abondon")
    private Boolean isAbondon;

    @Column(name = "state")
    private String state;

    @Column(name = "type")
    private String type;


    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void incCommentNumber() {
        this.commentNumber = this.commentNumber + 1;
    }

    public Long getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Long commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String indexUrl) {
        this.thumbnailUrl = indexUrl;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getAbondon() {
        return isAbondon;
    }

    public void setAbondon(Boolean abondon) {
        isAbondon = abondon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static VideoBroadcast create(String name, String url, String tag, String state, String thumbnailUrl, String introduction) {
        VideoBroadcast videoBroadcast = new VideoBroadcast();
        videoBroadcast.setName(name);
        videoBroadcast.setUrl(url);
        videoBroadcast.setTag(tag);
        videoBroadcast.setState(state);
        videoBroadcast.setIntroduction(introduction);
        videoBroadcast.setThumbnailUrl(thumbnailUrl);
        videoBroadcast.setAbondon(false);
        videoBroadcast.setCommentNumber(0L);
        return videoBroadcast;
    }

    private VideoBroadcast() {
        type = "normal";
    }


    @Override
    public String toString() {
        return "VideoBroadcast{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", thumbnailUrl='" + thumbnailUrl + '\'' +
            ", tag='" + tag + '\'' +
            ", commentNumber=" + commentNumber +
            ", introduction='" + introduction + '\'' +
            ", isAbondon=" + isAbondon +
            ", state='" + state + '\'' +
            ", type='" + type + '\'' +
            '}';
    }

    public void deleteVideo() {
        this.type = "delete";
    }

}
