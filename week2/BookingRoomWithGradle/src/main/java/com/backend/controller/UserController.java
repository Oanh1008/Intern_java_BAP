package com.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
//
    @GetMapping("/403")
    public String deniedPage(){
        return "403";
    }
}
