package com.group.rua.Session.log_in;
import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Autentica al usuario.
     * En la nueva BD, si el usuario existe en 'users' ya está verificado
     * (la verificación mueve el registro de 'unconfirmed_user' a 'users').
     */
    public boolean authenticate(String mail, String plainPassword) {
        Optional<User> optUser = userRepo.findByMail(mail);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return passwordEncoder.matches(plainPassword, user.hashedPassword);
        }
        return false;
    }
}
