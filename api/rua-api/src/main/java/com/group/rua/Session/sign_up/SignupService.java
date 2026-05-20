package com.group.rua.Session.sign_up;

import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.UserRepo;

@Service
public class SignupService {
    /**
     * TODO: Falta añadir que token pueda expirar
     */

    //Link que el usuario tiene que dar click para confirmar su identidad
    private String confirmationLink = "http://localhost:8080/account/verify_email";
    //Email host de la pagina SendGrid
    private String hostEmail = "t.jaures01@ufromail.cl";

    /**
     * Generar token, encriptar contraseña, guardar en la BD y enviar correo
     */
    public void createUser(User user){
        String token = generateToken();
        user.tokenConfirmation = token;
        user.contrasena = encryptPassword(user.contrasena);

        saveUser(user);
        sendConfirmationEmail(user.correo, token);
    }

    /**
     * Confirmar que el token recien creado si existe
     */
    public boolean verifyToken(String token){
        Optional<User> optUser = userTable.findByTokenConfirmation(token);
        if (optUser.isPresent()) {
            updateConfirmedUser(optUser.get());
            return true;
        } else {
            return false;
        }
    }

    public void updateConfirmedUser(User user){
        user.correo_verificado = "true";
        user.tokenConfirmation = null;
        userTable.save(user);
    }


    public void saveUser(User user){
        userTable.save(user);
    }

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    public void sendConfirmationEmail(String destinationEmail, String token){
        SimpleMailMessage message = new SimpleMailMessage();

        String link = confirmationLink + "?token_verificacion=" + token;

        message.setFrom(hostEmail);
        message.setTo(destinationEmail);

        message.setSubject("Verificación de cuenta");
        message.setText("Confirma tu correo!!!: " + link);

        mailSender.send(message);
    }


    private final UserRepo userTable;
    private JavaMailSender mailSender;
    private BCryptPasswordEncoder passwordEncoder;
    public SignupService(UserRepo userTable, JavaMailSender mailSender, BCryptPasswordEncoder passwordEncoder){
        this.userTable = userTable;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }


}
