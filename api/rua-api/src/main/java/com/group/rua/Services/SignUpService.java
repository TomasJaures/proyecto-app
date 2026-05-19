package com.group.rua.Services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group.rua.Entities_Classes.User;

@Service
public class SignUpService {

    private BCryptPasswordEncoder passwordEncoder;
    public SignUpService(BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public void encryptPassword(User user){
        String encryptedPassword = passwordEncoder.encode(user.contrasena);
        user.contrasena = encryptedPassword;
    }

}
