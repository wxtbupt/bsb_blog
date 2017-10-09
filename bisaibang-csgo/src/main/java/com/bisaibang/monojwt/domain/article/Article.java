package com.bisaibang.monojwt.domain.article;

import com.bisaibang.monojwt.domain.people.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "author_name")
    private String authorName;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "edit_date")
    private ZonedDateTime editDate;

    @Column(name = "type")
    private String type;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "state")
    private String state;

    @Column(name = "is_abandon")
    private Boolean isAbandon;

    @Column(name = "comment_number")
    private Long commentNumber;

    @ManyToOne
    private User creator;

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", name='" + name + '\'' +
            ", authorName='" + authorName + '\'' +
            ", content='" + content + '\'' +
            ", createDate=" + createDate +
            ", editDate=" + editDate +
            ", type='" + type + '\'' +
            ", introduction='" + introduction + '\'' +
            ", thumbnailUrl='" + thumbnailUrl + '\'' +
            ", state='" + state + '\'' +
            ", isAbandon=" + isAbandon +
            ", commentNumber=" + commentNumber +
            ", creator=" + creator +
            '}';
    }

    public Long getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Long commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Article title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public Article name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Article authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public Article content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Article createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getEditDate() {
        return editDate;
    }

    public Article editDate(ZonedDateTime editDate) {
        this.editDate = editDate;
        return this;
    }

    public void setEditDate(ZonedDateTime editDate) {
        this.editDate = editDate;
    }

    public String getType() {
        return type;
    }

    public Article type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public Article state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean isIsAbandon() {
        return isAbandon;
    }

    public Article isAbandon(Boolean isAbandon) {
        this.isAbandon = isAbandon;
        return this;
    }

    public void setIsAbandon(Boolean isAbandon) {
        this.isAbandon = isAbandon;
    }


    public User getCreator() {
        return creator;
    }

    public Article creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        if (article.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Article create(Article data ){
        Article art = new Article();
        art.setName(data.getName());
        art.setTitle(data.getTitle());
        art.setContent(data.getContent());
        art.setIntroduction(data.getIntroduction());
        art.setThumbnailUrl(data.getThumbnailUrl());
        art.setIsAbandon(false);
        art.setCreateDate(ZonedDateTime.now());
        art.setEditDate(ZonedDateTime.now());
        art.setCommentNumber(0L);
        return art;
    }

    private Article() {
        state = "normal";
        type = "normal";
    }

    public void incCommentNumber(){
        this.commentNumber = this.commentNumber + 1;
    }

    public void deleteArticle(){
        this.type = "delete";
    }
}
