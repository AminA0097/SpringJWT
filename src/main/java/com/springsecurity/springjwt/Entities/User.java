package com.springsecurity.springjwt.Entities;

import com.springsecurity.springjwt.Base.BaseEntity;
import com.springsecurity.springjwt.Base.BooleanConvertor;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "USERTABLE")
public class User extends BaseEntity {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "FLD_USER_ID", nullable = false)
    private Long id;

    @Column(name = "FLD_USER_NAME",nullable = true, length = 255)
    private String usernamee;

    @Column(name = "FLD_PASSWORD",length = 255)
    private String password;

    @Column(name = "FLD_SYSROLE")
    private String sysRole;

    @Column(name = "FLD_ENABLE")
    @Convert(converter = BooleanConvertor.class)
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
