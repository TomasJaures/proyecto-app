package com.group.rua.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HelloApplication {
    @GetMapping("/")
    public String inicio(Model model) {
        
        model.addAttribute("mensaje", "Hola desde Thymeleaf");

        return "index";
    }

    String hello(){
        return "Hello World desde la WEB!";
    }
}
