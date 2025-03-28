package com.springsecurity.springjwt.Dto;

public class UserDto {
    private String usernamee;
    private String sysRole;
    private boolean enabled;
    private String updated;

    public String getUsernamee() {
        return usernamee;
    }

    public void setUsernamee(String usernamee) {
        this.usernamee = usernamee;
    }

    public String getSysRole() {
        return sysRole;
    }

    public void setSysRole(String sysRole) {
        this.sysRole = sysRole;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
