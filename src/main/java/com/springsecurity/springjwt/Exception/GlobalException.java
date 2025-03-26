package com.springsecurity.springjwt.Exception;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalException {

   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<Map<String,String>> handleBadCredentialsException() {
       return createErrorResponse("Login Failed","Invalid UserName Or PassWord!",
               HttpStatus.UNAUTHORIZED);
   }
   @ExceptionHandler(SignUpError.class)
   public ResponseEntity<Map<String,String>> signUpError() {
       return createErrorResponse("SignUp Failed","User already exists!",
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
