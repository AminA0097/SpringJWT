package com.springsecurity.springjwt.Repo;


import com.springsecurity.springjwt.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByusernamee(String username)throws UsernameNotFoundException;
}
