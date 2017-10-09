package com.bisaibang.monojwt.domain.chat;

import com.bisaibang.monojwt.domain.people.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ChatLog.
 */
@Entity
@Table(name = "chat_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChatLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    private User toWhom;

    @ManyToOne
    private User fromWhom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public ChatLog message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public User getToWhom() {
        return toWhom;
    }

    public ChatLog toWhom(User user) {
        this.toWhom = user;
        return this;
    }

    public void setToWhom(User user) {
        this.toWhom = user;
    }

    public User getFromWhom() {
        return fromWhom;
    }

    public ChatLog fromWhom(User user) {
        this.fromWhom = user;
        return this;
    }

    public void setFromWhom(User user) {
        this.fromWhom = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatLog chatLog = (ChatLog) o;
        if (chatLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chatLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChatLog{" +
            "id=" + id +
            ", message='" + message + "'" +
            '}';
    }

    private ChatLog() {
        date = ZonedDateTime.now();
    }

    public static ChatLog createTest(String content) {
        ChatLog chatLog = new ChatLog();
        chatLog.setMessage(content);
        return chatLog;
    }
}
