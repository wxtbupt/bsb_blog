package com.bisaibang.monojwt.domain.forum;

import com.bisaibang.monojwt.domain.people.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "edit_date")
    private ZonedDateTime editDate;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "state")
    private String state;

    @ManyToOne
    private User creator;

    @ManyToOne
    private SingleThread singleThread;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(ZonedDateTime editDate) {
        this.editDate = editDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public SingleThread getSingleThread() {
        return singleThread;
    }

    public void setSingleThread(SingleThread singleThread) {
        this.singleThread = singleThread;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", authorName='" + authorName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", state='" + state + '\'' +
                ", creator=" + creator +
                ", singleThread=" + singleThread +
                '}';
    }

    private Post(){

    }

    public static Post create(String title,String content,String state,SingleThread singleThread) {
        Post p = new Post();
        p.setTitle(title);
        p.setContent(content);
        p.setState("normal");
        p.setSingleThread(singleThread);
        p.setCreateDate(ZonedDateTime.now());
        p.setEditDate(ZonedDateTime.now());
        return p;
    }

    public void deletePost() {
        this.state = "delete";
    }

}
