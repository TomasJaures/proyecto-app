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

    public boolean authenticate(String correo, String contrasenaPlana) {
        Optional<User> optUser = userRepo.findByCorreo(correo);

        if (optUser.isPresent()) {
            User user = optUser.get();
            if (!"true".equals(user.correo_verificado)) {
                return false;
            }
            return passwordEncoder.matches(contrasenaPlana, user.contrasena);
        }

        return false;
    }
}
