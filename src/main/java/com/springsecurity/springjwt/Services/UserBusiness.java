package com.springsecurity.springjwt.Services;

import com.springsecurity.springjwt.Dto.*;
import com.springsecurity.springjwt.Entities.User;
import com.springsecurity.springjwt.Exception.UserErr;
import com.springsecurity.springjwt.JwtService.JwtService;
import com.springsecurity.springjwt.JwtService.UserDetailImp;
import com.springsecurity.springjwt.Repo.UserRepo;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserBusiness {
    private final PasswordEncoder passwordEncoder;

    public UserBusiness(UserRepo userRepo,
                        JwtService jwtService,
                        AuthenticationManager authenticationManager,
                        PasswordEncoder passwordEncoder,
                        Mapper mappers
                        ) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.mappers = mappers;

    }

    @Autowired
    private EntityManager entityManager;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Mapper mappers;


    public String signin(Login login) {
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        User user = userRepo.findByusernamee(login.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(login.getUsername()));
        String jwt = jwtService.generateToken(new UserDetailImp(user));;
        return jwt;
    }

    @Transactional
    public String signup(SignUp signUp) {
        String query = "select count(*) from User u where u.usernamee=:username";
        List rs = entityManager.createQuery(query)
                .setParameter("username", signUp.getUserName()).getResultList();
        if(!signUp.getPassword().equals(signUp.getConfirmPassword())) {
            throw new UserErr("PassWord Does Not Match");
        }
        if (Integer.parseInt(rs.get(0).toString()) > 0) {
            throw new UserErr("Username already exists");
        }
        ;
        User user = new User();
        user.setUsernamee(signUp.getUserName());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setDeleted(false);
        user.setEnabled(true);
        user.setSysRole("USER");
        user.setCreated("SYS");
        user.setUpdated("SYS");
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        entityManager.persist(user);
        entityManager.flush();
        return signUp.getUserName();
    }

    @Transactional
    public String changeRole(ChangeRole changeRole) {
        User user = entityManager.createQuery("SELECT p FROM User p WHERE p.usernamee = :name", User.class)
                .setParameter("name", changeRole.getUsername())
                .getSingleResult();
        user.setSysRole(changeRole.getRole());
        user.setUpdatedDate(new Date());
        user.setUpdated(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setUsernamee(changeRole.getUsername());
        System.out.println(user.toString());
        entityManager.merge(user);
        return String.format("Role Of %s Changed By %s", changeRole.getUsername(),
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
    @Transactional
    public String changeActivation(Activation activation) {
        User user = entityManager.createQuery("SELECT p FROM User p WHERE p.usernamee = :name", User.class)
                .setParameter("name", activation.getUsername())
                .getSingleResult();
        user.setEnabled(false);
        user.setUpdatedDate(new Date());
        user.setUpdated(SecurityContextHolder.getContext().getAuthentication().getName());
        entityManager.merge(user);
        return String.format("%s DeActivated! By %s",activation.getUsername(), SecurityContextHolder.getContext().getAuthentication().getName());
    }
    @Transactional
    public List<UserDto> getAll() {
        List<User> users = userRepo.findAll();
        return mappers.userToDto(users);
    }
}
