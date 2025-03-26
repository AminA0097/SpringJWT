package com.springsecurity.springjwt.UserService;

import com.springsecurity.springjwt.Entities.User;

import java.util.List;

public interface  UserService {
    List<User> findUser();
}
