package com.springsecurity.springjwt.Service;

import com.springsecurity.springjwt.Dto.Login;
import com.springsecurity.springjwt.Entities.User;
import com.springsecurity.springjwt.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class AuthenticationService {
    public AuthenticationService(UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String signin(Login login) {
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        User user = userRepo.findByusernamee(login.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(login.getUsername()));
        String jwt = jwtService.generateToken(new UserDetailImp(user));
        return jwt;
    }


}
