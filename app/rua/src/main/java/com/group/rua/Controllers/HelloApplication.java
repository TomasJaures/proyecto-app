package com.group.rua.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelloApplication {
    @GetMapping("/")
    String hello(){
        return "Hello World desde RENDER!!!";
    }
}
