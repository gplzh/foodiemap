package com.btw.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;

/**
 * Created by gplzh on 2016/5/29.
 */


@Entity
@Table(name = "user")
@ApiModel
public class User {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "sex")
    private Boolean sex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
