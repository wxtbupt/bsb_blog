package com.bisaibang.monojwt.domain.article;

import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.domain.video.VideoBroadcast;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "edit_date")
    private ZonedDateTime editDate;

    @Column(name = "state")
    private String state;

    @Column(name = "is_abandon")
    private Boolean isAbandon;

    @Column(name = "level")
    private String level;

    @ManyToOne
    private Comment leaderComment;

    @ManyToOne
    private User creator;

    @ManyToOne
    private Article article;

    @ManyToOne
    private VideoBroadcast video;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Comment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Comment authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public Comment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public Comment type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Comment createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getEditDate() {
        return editDate;
    }

    public Comment editDate(ZonedDateTime editDate) {
        this.editDate = editDate;
        return this;
    }

    public void setEditDate(ZonedDateTime editDate) {
        this.editDate = editDate;
    }

    public String getState() {
        return state;
    }

    public Comment state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getIsAbandon() {
        return isAbandon;
    }

    public Comment isAbandon(Boolean isAbandon) {
        this.isAbandon = isAbandon;
        return this;
    }

    public void setIsAbandon(Boolean isAbandon) {
        this.isAbandon = isAbandon;
    }

    public User getCreator() {
        return creator;
    }

    public Comment creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public Article getArticle() {
        return article;
    }

    public Comment article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Boolean getAbandon() {
        return isAbandon;
    }

    public void setAbandon(Boolean abandon) {
        isAbandon = abandon;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Comment getLeaderComment() {
        return leaderComment;
    }

    public void setLeaderComment(Comment leaderComment) {
        this.leaderComment = leaderComment;
    }

    public VideoBroadcast getVideo() {
        return video;
    }

    public void setVideo(VideoBroadcast video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", state='" + state + '\'' +
                ", isAbandon=" + isAbandon +
                ", level='" + level + '\'' +
                ", leaderComment=" + leaderComment +
                ", creator=" + creator +
                ", article=" + article +
                ", video=" + video +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private Comment() {
        type = "normal";
    }

    public static Comment create(Comment data) {
        Comment c = new Comment();
        c.setIsAbandon(false);
        c.setContent(data.getContent());
        c.setTitle(data.getTitle());
        c.setState(data.getState());
        c.setLevel(data.getLevel());
        c.setCreateDate(ZonedDateTime.now());
        c.setEditDate(ZonedDateTime.now());
        if (data.oneLevel()) {
            c.setArticle(data.getArticle());
        } else if (data.twoLevel()) {
            c.setArticle(data.getArticle());
            c.setLeaderComment(data.getLeaderComment());
        } else if (data.getLevel().equals("11")) {
            c.setVideo(data.getVideo());
        } else if (data.getLevel().equals("12")) {
            c.setVideo(data.getVideo());
            c.setLeaderComment(data.getLeaderComment());
        }
        return c;
    }

    public Boolean oneLevel() {
        return level.equals("1");
    }

    public Boolean twoLevel() {
        return level.equals("2");
    }

    public void deleteComment() {
        this.type = "delete";
    }

}
