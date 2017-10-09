package com.bisaibang.monojwt.domain.game;

import com.bisaibang.monojwt.domain.people.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by Lynn on 2017/3/22.
 */
@Entity
@Table(name = "card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Card {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private Registration registration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", registration=" + registration +
                '}';
    }

    public static Card create(Card data){
        Card tp = new Card();
        return tp;
    }

}
