package com.bisaibang.monojwt.domain.forum;

import com.bisaibang.monojwt.domain.people.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SingleThread.
 */
@Entity
@Table(name = "single_thread")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SingleThread implements Serializable {

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

    @Column(name = "view_num")
    private Long viewNum;

    @Column(name = "post_num")
    private Long postNum;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "edit_date")
    private ZonedDateTime editDate;

    @Column(name = "state")
    private String state;

    @Column(name = "section")
    private String section;

    @Column(name = "color")
    private String color;

    @Column(name = "latest_comment_user")
    private String latestCommentUser;

    @ManyToOne
    private Forum forum;

    @ManyToOne
    private User creator;

    @Override
    public String toString() {
        return "SingleThread{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", authorName='" + authorName + '\'' +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", viewNum=" + viewNum +
            ", postNum=" + postNum +
            ", createDate=" + createDate +
            ", editDate=" + editDate +
            ", state='" + state + '\'' +
            ", section='" + section + '\'' +
            ", color='" + color + '\'' +
            ", latestCommentUser='" + latestCommentUser + '\'' +
            ", forum=" + forum +
            ", creator=" + creator +
            '}';
    }

    public String getLatestCommentUser() {
        return latestCommentUser;
    }

    public void setLatestCommentUser(String latestCommentUser) {
        this.latestCommentUser = latestCommentUser;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public SingleThread name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public SingleThread authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public SingleThread title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public SingleThread content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getViewNum() {
        return viewNum;
    }

    public SingleThread viewNum(Long viewNum) {
        this.viewNum = viewNum;
        return this;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
    }

    public Long getPostNum() {
        return postNum;
    }

    public SingleThread postNum(Long postNum) {
        this.postNum = postNum;
        return this;
    }

    public void setPostNum(Long postNum) {
        this.postNum = postNum;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public SingleThread createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getEditDate() {
        return editDate;
    }

    public SingleThread editDate(ZonedDateTime editDate) {
        this.editDate = editDate;
        return this;
    }

    public void setEditDate(ZonedDateTime editDate) {
        this.editDate = editDate;
    }


    public String getState() {
        return state;
    }

    public SingleThread state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Forum getForum() {
        return forum;
    }

    public SingleThread forum(Forum forum) {
        this.forum = forum;
        return this;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public User getCreator() {
        return creator;
    }

    public SingleThread creator(User user) {
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
        SingleThread singleThread = (SingleThread) o;
        if (singleThread.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, singleThread.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static SingleThread create(Forum forum, SingleThread data){
        SingleThread sT = new SingleThread();
        sT.setContent(data.getContent());
        sT.setName(data.getName());
        sT.setTitle(data.getTitle());
        sT.setForum(forum);
        sT.setState("normal");
        sT.setColor(data.getColor());
        sT.setCreateDate(ZonedDateTime.now());
        sT.setEditDate(ZonedDateTime.now());
        sT.setViewNum(0L);
        sT.setPostNum(0L);
        return sT;
    }

    public static SingleThread createDefault(Forum forum, User user, String name){
        SingleThread sT = new SingleThread();
        sT.setContent("<p>亲爱的各位朋友：</p><p>欢迎来到" + name + "小站！</p><p>您可以在本小站内发表帖子，参加比赛，并与其它来到这里的朋友分享交流比赛心得与经验。</p><p>祝您在小站玩得愉快~</p><p>——比赛帮官方<br/></p><p><img style=\"width: 100%;\" src=\"https://cdn1.bisaibang.com/ArticleImage.1500875273163.jpeg\"/>\u200B</p>");
        sT.setName("嗨，欢迎来到本小站，您可以在此畅所欲言！");
        sT.setTitle("嗨，欢迎来到本小站，您可以在此畅所欲言！");
        sT.setForum(forum);
        sT.setState("normal");
        sT.setColor("这里写颜色");
        sT.setCreateDate(ZonedDateTime.now());
        sT.setEditDate(ZonedDateTime.now());
        sT.setViewNum(0L);
        sT.setPostNum(0L);

        sT.setCreator(user);
        sT.setAuthorName(user.getNickName());
        return sT;
    }

    public static SingleThread create(Long id) {
        SingleThread t = new SingleThread();
        t.setId(id);
        return t;
    }

    public void incPostNumber(){
        this.postNum = this.postNum + 1;
    }

    public void incViewNumber(){
        this.viewNum = this.viewNum + 1;
    }


    public void deleteSingleThread() {
        this.state = "delete";
    }

    public void markSingleThread() {
        this.state = "essence";
    }

    public void revertMarkSingleThread() {
        this.state = "normal";
    }

}
