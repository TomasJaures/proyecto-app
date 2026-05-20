package com.group.rua.Controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    
    /**
     * De momento lo redirecciona a Log In, proximamente seguramente haya mas logica
     */
    @GetMapping
    public ResponseEntity<Void> loginRedirect(){
        URI uri = URI.create("/login");
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }
}