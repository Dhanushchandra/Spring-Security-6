package com.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @GetMapping("/home")
    public String handleHome(){
        return "home";
    }

    @GetMapping("/admin")
    public String handleAdmin(){
        return "admin";
    }

    @GetMapping("/user")
    public String handleUser(){
        return "user";
    }

}
