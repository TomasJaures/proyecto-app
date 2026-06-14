package com.group.rua.Session.log_in;

import com.group.rua.Entities_Classes.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:1427")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //TODO: Falta el rol
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginData) {
        boolean isAuthenticated = loginService.authenticate(loginData.mail, loginData.hashedPassword);

        if (isAuthenticated) {
            System.out.println("Esta autenticado");
            return ResponseEntity.ok("Inicio de sesión exitoso. ¡Bienvenido!");
        } else {
            System.out.println("No Esta autenticado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas o correo no verificado.");
        }
    }
}