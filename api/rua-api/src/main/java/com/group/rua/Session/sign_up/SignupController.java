package com.group.rua.Session.sign_up;
import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.group.rua.Entities_Classes.User;

@RestController
@RequestMapping("/account")
public class SignupController {

    
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody User user){
        
        signupService.createUser(user);

        URI uri = URI.create("redirect:http://localhost:8080/account/email_sended");
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    @GetMapping("/verify_email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token_verificacion){

        //Si en el futuro da u
        String success = "redirect:http://localhost:8080/account/confirmation_success";
        String fail = "redirect:http://localhost:8080/account/confirmation_fail";
        boolean result = signupService.verifyToken(token_verificacion);

        URI uri = URI.create(result ? success : fail);
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    @GetMapping("/email_sended")
    public String notifyEmailSended(){
        return "Te hemos enviado un EMAIL!! Revisa tu correo de SPAM";
    }

    @GetMapping("/confirmation_success")
    public String confirmationSuccess(){
        return "Correo verificado correctamente! Puedes volver al Log In";
    }

    @GetMapping("/confirmation_fail")
    public String confirmationFail(){
        return "Hubo un problema con tu correo :(";
    }

    private final SignupService signupService;
    public SignupController(SignupService signupService){
        this.signupService = signupService;
    }
}
