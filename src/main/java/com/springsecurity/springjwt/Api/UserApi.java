package com.springsecurity.springjwt.Api;

import com.springsecurity.springjwt.Dto.*;
import com.springsecurity.springjwt.Entities.User;
import com.springsecurity.springjwt.Services.UserBusiness;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@RestController
public class UserApi {
    private final UserBusiness userBusiness;

    public UserApi(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @PostMapping("/signin/")
    public String signin(@RequestBody Login login) {
        return userBusiness.signin(login);
    }
    @PostMapping("/signup/")
    public String signup(@RequestBody SignUp signUp){
        return userBusiness.signup(signUp);
    }
    @PostMapping("/changerole/")
    public String changeRole(@RequestBody ChangeRole changeRole){
        return userBusiness.changeRole(changeRole);
    }
    @PostMapping("/activation/")
    public String changeActivation(@RequestBody Activation activation){
        return userBusiness.changeActivation(activation);
    }
    @GetMapping("/getall/")
    public List<UserDto> getAll(){
        return userBusiness.getAll();
    }
}
