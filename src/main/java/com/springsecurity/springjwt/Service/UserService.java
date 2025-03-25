package com.springsecurity.springjwt.Service;

import com.springsecurity.springjwt.Dto.Login;
import com.springsecurity.springjwt.Entities.User;

import java.util.List;

public interface  UserService {
    List<User> findUser();
}
