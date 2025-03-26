package com.springsecurity.springjwt.Config;

import com.springsecurity.springjwt.Exception.JWTException;
import com.springsecurity.springjwt.Filter.CustomFilter;
import com.springsecurity.springjwt.JwtService.UserServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final CustomFilter customFilter;
    private final UserServiceImpl  userService;
    private final JWTException jwtException;

    public SecurityConfig(
    CustomFilter customFilter,
    UserServiceImpl userService,
    JWTException jwtException) {
        this.customFilter = customFilter;
        this.userService = userService;
        this.jwtException = jwtException;

    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.getUserDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authReq -> authReq.requestMatchers(HttpMethod.POST,"users/signin/**").permitAll()
                .requestMatchers(HttpMethod.POST,"users/signup/**").permitAll());
        http.authenticationProvider(authenticationProvider());
        http.logout(logout -> logout.logoutSuccessUrl("/users/signin/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                );
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtException));
        http.headers(headers -> headers.cacheControl(cash -> cash.disable()));
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
