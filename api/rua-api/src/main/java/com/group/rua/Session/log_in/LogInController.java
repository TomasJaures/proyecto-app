package com.group.rua.Session.log_in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LogInController {
    
    @GetMapping
    public String show(){
        return "Estas en Log In!!";
    }
}
