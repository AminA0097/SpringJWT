package com.springsecurity.springjwt.JwtService;

import com.springsecurity.springjwt.Dto.ChangeRole;
import com.springsecurity.springjwt.Dto.Login;
import com.springsecurity.springjwt.Dto.SignUp;
import com.springsecurity.springjwt.Entities.User;
import com.springsecurity.springjwt.Exception.SignUpError;
import com.springsecurity.springjwt.Repo.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

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


@Service
public class UserBusiness {
    private final PasswordEncoder passwordEncoder;

    public UserBusiness(UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    private EntityManager entityManager;
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
    @Transactional
    public String signup(SignUp signUp){
        String query = "select count(*) from User u where u.usernamee=:username";
        List rs = entityManager.createQuery(query)
                .setParameter("username",signUp.getUserName()).getResultList();
        if (Integer.parseInt(rs.get(0).toString()) > 0) {
            throw new SignUpError();
        };
        User user = new User();
        user.setUsernamee(signUp.getUserName());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setDeleted(false);
        user.setEnabled(true);
        user.setSysRole("USER");
        user.setCreated("BySYS");
        user.setUpdated("BYSYS");
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        Update(user);
        return signUp.getUserName();
    }
    @Transactional
    public boolean changeRole(ChangeRole changeRole) {
        User user = entityManager.createQuery("SELECT p FROM User p WHERE p.usernamee = :name", User.class)
                .setParameter("name", changeRole.getUsername())
                .getSingleResult();
        user.setSysRole(changeRole.getRole());
        user.setUpdatedDate(new Date());
        user.setUpdated(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setUsernamee(changeRole.getUsername());
        System.out.println(user.toString());
        Update(user);
        return true;
    }
    @Transactional
    public void Update(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }
}
