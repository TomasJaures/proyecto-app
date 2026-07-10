package com.group.rua.session.log_in;

import com.group.rua.RuaConfig;
import com.group.rua.entities.User;
import com.group.rua.repositories.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/account")
@CrossOrigin(origins = RuaConfig.FRONTEND_URL)
public class LoginController {

    private final UserRepo userRepo;
    private final LoginService loginService;

    public LoginController(LoginService loginService, UserRepo userRepo) {
        this.loginService = loginService;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDTO loginData) {

        boolean isAuthenticated = loginService.authenticate(loginData.mail, loginData.hashedPassword);

        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o correo no verificado.");
        }

        Optional<User> userOpt = userRepo.findByMail(loginData.mail);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario inexistente");
        }

        User user = userOpt.get();
    
    Map<String, Object> response = new HashMap<>();

    response.put("id", user.userId);
    response.put("name", user.userName);
    response.put("lastName1", user.lastName1);
    response.put("lastName2", user.lastName2);
    response.put("programId", user.program.programId);
    response.put("calendarId", user.calendar.calendarId);

    if (loginData.mail.endsWith("@ufromail.cl")) {
        response.put("role", "student");
    } else {
        response.put("role", "professor");
    }

    return ResponseEntity.ok(response);
}

}
