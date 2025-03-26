package com.springsecurity.springjwt.Api;

import com.springsecurity.springjwt.Dto.Login;
import com.springsecurity.springjwt.Dto.SignUp;
import com.springsecurity.springjwt.Entities.User;
import com.springsecurity.springjwt.JwtService.AuthenticationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserApi {
    private final AuthenticationService authenticationService;

    public UserApi(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin/")
    public String signin(@RequestBody Login login) {
        return authenticationService.signin(login);
    }

    @PostMapping("/signup/")
    public String signup(@RequestBody SignUp signUp){
        return authenticationService.signup(signUp);
    }
}
