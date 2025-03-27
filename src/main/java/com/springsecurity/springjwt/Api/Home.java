package com.springsecurity.springjwt.Api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping()
public class Home {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
