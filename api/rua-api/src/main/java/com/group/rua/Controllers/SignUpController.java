package com.group.rua.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.UserRepo;
import com.group.rua.Services.SignUpService;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    
    private final UserRepo userTable;
    private final SignUpService service;
    public SignUpController(UserRepo userTable, SignUpService service){
        this.userTable = userTable;
        this.service = service;
    }

    @PostMapping
    public User crearUsuario(@RequestBody User user) {
        service.encryptPassword(user);
        return userTable.save(user);
    }
}

/* 

Probar cambios (el correo es necesario cambiarlo)

    Invoke-RestMethod `
    -Uri "http://localhost:8080/signup" `
    -Method POST `
    -ContentType "application/json" `
    -Body '{
    "nombre":"Tomas",
    "apellido1":"Jaures",
    "apellido2":"Painen",
    "correo":"t.jaures01@ufromail.cl",
    "contrasena":"12345678",
    "correo_verificado":"false",
    "token_verificacion":"abc1234"
    }'
*/