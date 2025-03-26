package com.springsecurity.springjwt.JwtService;
import com.springsecurity.springjwt.Entities.User;
import com.springsecurity.springjwt.Repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl{
    private final UserRepo userRepo;
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                 User user = userRepo.findByusernamee(username)
                        .orElseThrow(() -> new UsernameNotFoundException(""));
                return new UserDetailImp(user);
            }
        };
    }


}
