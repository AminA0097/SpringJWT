package com.springsecurity.springjwt.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(AuthenticationException.class)
    public  ResponseEntity<Map<String,String>> authenticationException() {
        return createErrorResponse("Access Denied!","You Dont Hava Permission!",
                HttpStatus.UNAUTHORIZED);
    }
   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<Map<String,String>> handleBadCredentialsException() {
       return createErrorResponse("Login Failed","Invalid UserName Or PassWord!",
               HttpStatus.UNAUTHORIZED);
   }
   @ExceptionHandler(UserErr.class)
   public ResponseEntity<Map<String,String>> signUpError(UserErr userErr) {
       return createErrorResponse("Err!",userErr.getMessage(),
               HttpStatus.UNAUTHORIZED);
   }

    private ResponseEntity<Map<String, String>> createErrorResponse(
            String problem,String msg,
            HttpStatus status) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", problem);
        errors.put("message",msg);
        return new ResponseEntity<>(errors,status);
   }

}
