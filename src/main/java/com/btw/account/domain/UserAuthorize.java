package com.btw.account.domain;

import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;

/**
 * Created by gplzh on 2016/5/29.
 */

@Entity
@Table(name = "user_auth")
@ApiModel
public class UserAuthorize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "identity_type")
    private int identityType;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "credential")
    private String credential;

    @Column(name = "verified")
    private Boolean verified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
