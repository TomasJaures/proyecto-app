package com.group.rua.Session.log_in;

import com.group.rua.Entities_Classes.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginData) {
        boolean isAuthenticated = loginService.authenticate(loginData.correo, loginData.contrasena);

        if (isAuthenticated) {
            return ResponseEntity.ok("Inicio de sesión exitoso. ¡Bienvenido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas o correo no verificado.");
        }
    }
}