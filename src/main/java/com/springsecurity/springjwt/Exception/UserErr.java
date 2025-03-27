package com.springsecurity.springjwt.Exception;

public class UserErr extends RuntimeException{
    public UserErr(String msg) {
        super(msg);
    }
}
