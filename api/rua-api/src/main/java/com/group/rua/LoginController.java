package com.group.rua;

import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {

        System.out.println(req.getNombre());
        System.out.println(req.getPassword());

        if (
            req.getNombre().equals("tomas") &&
            req.getPassword().equals("1234")
        ) {

            String token = UUID.randomUUID().toString();

            return Map.of(
                "token", token
            );
        }

          throw new RuntimeException("Login incorrecto");
    }

}