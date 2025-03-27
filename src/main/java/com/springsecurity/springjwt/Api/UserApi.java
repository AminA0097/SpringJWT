package com.springsecurity.springjwt.Api;

import com.springsecurity.springjwt.Dto.ChangeRole;
import com.springsecurity.springjwt.Dto.Login;
import com.springsecurity.springjwt.Dto.SignUp;
import com.springsecurity.springjwt.Services.UserBusiness;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@Controller
public class UserApi {
    private final UserBusiness userBusiness;

    public UserApi(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @PostMapping("/signin/")
    public String signin(@RequestBody Login login, Model model) {
        String token = userBusiness.signin(login);
        model.addAttribute("token", token);
        model.addAttribute("name", login.getUsername());
        return "home";
    }
    @GetMapping("/home/")
    public String home() {
        return "home";
    }
    @PostMapping("/signup/")
    public String signup(@RequestBody SignUp signUp){
        return userBusiness.signup(signUp);
    }
    @PostMapping("/changerole/")
    public boolean changeRole(@RequestBody ChangeRole changeRole){
        return userBusiness.changeRole(changeRole);
    }
}
