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
import org.springframework.web.bind.annotation.CrossOrigin;

import com.group.rua.RuaConfig;
import com.group.rua.Entities_Classes.UnconfirmedUser;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    /**
     * Registra un nuevo usuario (pendiente de verificación de correo).
     * Guarda en 'unconfirmed_user' hasta que el correo sea confirmado.
     *
     * @param user : JSON con los datos del usuario (userName, lastName1, mail, hashedPassword, etc.)
     * @return Redirección a email_sended
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UnconfirmedUser user) {
        signupService.createUser(user);

        URI uri = URI.create(RuaConfig.BACKEND_URL + "/account/email_sended");
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(uri)
                .build();
    }

    /**
     * Verifica el token recibido por correo y, si es válido,
     * mueve al usuario de 'unconfirmed_user' a 'users'.
     *
     * @param token_verificacion token UUID enviado por correo
     */
    @GetMapping("/verify_email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token_verificacion) {

        boolean result = signupService.verifyToken(token_verificacion);

        URI uri = URI.create(
            result ?
            RuaConfig.BACKEND_URL + "/account/confirmation_success" :
            RuaConfig.BACKEND_URL + "/account/confirmation_fail");
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    /**
     * ========== Páginas informando la situación de la confirmación por correo ==========
     */

    @GetMapping("/email_sended")
    public String notifyEmailSended() {
        return "Te hemos enviado un EMAIL!! Revisa tu correo de SPAM";
    }

    @GetMapping("/confirmation_success")
    public String confirmationSuccess() {
        return "Correo verificado correctamente! Puedes volver al Log In";
    }

    @GetMapping("/confirmation_fail")
    public String confirmationFail() {
        return "Hubo un problema con tu correo :(";
    }
}
