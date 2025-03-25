package com.springsecurity.springjwt.Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "USERTABLE")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FLD_USER_ID")
    private String id;

    @Column(name = "FLD_USER_NAME",nullable = true, length = 50)
    private String usernamee;

    @Column(name = "FLD_PASSWORD")
    private String password;

    @Column(name = "FLD_SYSROLE")
    private String sysRole;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsernamee() {
        return usernamee;
    }

    public void setUsernamee(String usernamee) {
        this.usernamee = usernamee;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSysRole() {
        return sysRole;
    }

    public void setSysRole(String sysRole) {
        this.sysRole = sysRole;
    }
}
