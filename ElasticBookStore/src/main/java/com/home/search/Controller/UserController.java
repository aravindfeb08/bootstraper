package com.home.search.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class UserController {

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/login")
    public String login() {
        return "Hello World";
    }

    @GetMapping("/logout")
    public String logout() {
        return "Hello World";
    }
}
