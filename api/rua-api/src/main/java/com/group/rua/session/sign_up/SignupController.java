package com.group.rua.session.sign_up;


import com.group.rua.RuaConfig;
import com.group.rua.entities.UnconfirmedUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
     * @param dto : JSON con los datos del usuario (userName, lastName1, mail, hashedPassword, etc.)
     * @return Redirección a email_sended
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody SignupDTO dto) {

        UnconfirmedUser user = new UnconfirmedUser();
        user.userName = dto.userName;
        user.lastName1 = dto.lastName1;
        user.lastName2 = dto.lastName2;
        user.mail = dto.mail;
        user.hashedPassword = dto.hashedPassword;

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
     * @param confirmationTokenString token UUID enviado por correo
     */
    @GetMapping("/verify_email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String confirmationTokenString) {

        boolean result = signupService.verifyToken(confirmationTokenString);

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
    public ResponseEntity<String> notifyEmailSended() {
        return ResponseEntity.ok("Te hemos enviado un EMAIL!! Revisa tu correo de SPAM");
    }

    @GetMapping("/confirmation_success")
    public ResponseEntity<String> confirmationSuccess() {
        return ResponseEntity.ok("Correo verificado correctamente! Puedes volver al Log In");
    }

    @GetMapping("/confirmation_fail")
    public ResponseEntity<String> confirmationFail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hubo un problema con tu correo :(");
    }
}
