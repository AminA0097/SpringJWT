package com.springsecurity.springjwt.Api;

import com.springsecurity.springjwt.Dto.Login;
import com.springsecurity.springjwt.Entities.User;
import com.springsecurity.springjwt.Service.AuthenticationService;
import com.springsecurity.springjwt.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserApi {
    private final AuthenticationService authenticationService;

    public UserApi(AuthenticationService authenticationService
                   ) {

        this.authenticationService = authenticationService;

    }

    @GetMapping("/users/")
    public String getUsers() {
        return "getUsers()";
    }

    @PostMapping("/signin/")
    public String signin(@RequestBody Login login) {
        return authenticationService.signin(login);
    }
}
