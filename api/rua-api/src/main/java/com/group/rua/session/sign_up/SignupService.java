package com.group.rua.session.sign_up;

import com.group.rua.RuaConfig;
import com.group.rua.entities.*;
import com.group.rua.exceptions.DuplicatedInUnconfirmedUsers;
import com.group.rua.exceptions.DuplicatedInUsers;
import com.group.rua.general.EmailDesign;
import com.group.rua.repositories.ConfirmationTokenRepo;
import com.group.rua.repositories.UnconfirmedUserRepo;
import com.group.rua.repositories.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SignupService {

    private final String confirmationLink = RuaConfig.BACKEND_URL + "/account/verify_email";
    private final String hostEmail = "ruaaplicacion@gmail.com";

    private static final Logger logger = LoggerFactory.getLogger(SignupService.class);

    public void createUser(UnconfirmedUser user) {

        // Validar duplicados en BD
        validateDuplicatedUserInBD(user); 

        user.hashedPassword = encryptPassword(user.hashedPassword);
        UnconfirmedUser savedUser = saveUnconfirmedUser(user);

        String token = generateToken();
        saveConfirmationToken(savedUser, token);

        try {
            sendConfirmationEmail(user.mail, token);
        } catch (Exception e) {
            logger.error("ERROR EN EL ENVIO DE CORREO", e);
        }
    }

    public void validateDuplicatedUserInBD(UnconfirmedUser user){
        validateInstitutionalEmail(user);
        validateDuplicatedUnconfirmedUsers(user);
        validateDuplicatedUsers(user);
    }

    public void validateDuplicatedUnconfirmedUsers(UnconfirmedUser user){
        if (unconfirmedUserRepo.findByMail(user.mail).isPresent())
            throw new DuplicatedInUnconfirmedUsers("Correo ya registrado en tabla UnconfirmedUsers");
    }

    public void validateDuplicatedUsers(UnconfirmedUser user){
        if (userRepo.findByMail(user.mail).isPresent())
            throw new DuplicatedInUsers("Correo ya registrado en tabla Users");
    }

    public void validateInstitutionalEmail(UnconfirmedUser user) {
        String correoLower = user.mail.toLowerCase();
        if (!correoLower.endsWith("@ufrontera.cl") && !correoLower.endsWith("@ufromail.cl")) {
            throw new IllegalArgumentException("Debe utilizar un correo institucional válido (@ufrontera.cl o @ufromail.cl)");
        }
    }


    public boolean verifyToken(String token) {
        Optional<ConfirmationToken> optToken = confirmationTokenRepo.findByContent(token);

        if (optToken.isPresent()) {
            ConfirmationToken confirmationToken = optToken.get();
            promoteToConfirmedUser(confirmationToken.unconfirmedUser);
            unconfirmedUserRepo.delete(confirmationToken.unconfirmedUser);
            return true;
        }
        return false;
    }

    public void promoteToConfirmedUser(UnconfirmedUser unconfirmedUser) {
        User confirmedUser = new User();
        confirmedUser.userName = unconfirmedUser.userName;
        confirmedUser.lastName1 = unconfirmedUser.lastName1;
        confirmedUser.lastName2 = unconfirmedUser.lastName2;
        confirmedUser.mail = unconfirmedUser.mail;
        confirmedUser.hashedPassword = unconfirmedUser.hashedPassword;

        String role = asignRoleByMail(unconfirmedUser.mail);
        confirmedUser.userRole = role;        

        Calendar calendar = new Calendar();
        Program program = new Program();

        confirmedUser.calendar = calendar;
        confirmedUser.program = program;
        userRepo.save(confirmedUser);
    }

    public void sendConfirmationEmail(String destinationEmail, String token) throws MessagingException {
        String link = confirmationLink + "?confirmationTokenString=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(hostEmail);
        helper.setTo(destinationEmail);
        helper.setSubject("Verificación de cuenta");

        String html = EmailDesign.createDesign(link);
        helper.setText(html, true);

        System.out.println("TEST: LINK para confirmacion! {}" + link);
        mailSender.send(message);
    }

    public UnconfirmedUser saveUnconfirmedUser(UnconfirmedUser user) {
        return unconfirmedUserRepo.save(user);
    }

    public void saveConfirmationToken(UnconfirmedUser user, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.content = token;
        confirmationToken.createdAt = LocalDateTime.now();
        confirmationToken.expireAt = LocalDateTime.now().plusMinutes(5); //5 Minutos para confirmar
        confirmationToken.unconfirmedUser = user;
        confirmationTokenRepo.save(confirmationToken);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String asignRoleByMail(String mail){
        String correoLower = mail.toLowerCase();
        String role = "";
        if (correoLower.endsWith("@ufrontera.cl")) {
            role = "professor";
        } else if (correoLower.endsWith("@ufromail.cl")) {
            role = "student";
        }
        return role;
    }

    private final UnconfirmedUserRepo unconfirmedUserRepo;
    private final ConfirmationTokenRepo confirmationTokenRepo;
    private final UserRepo userRepo;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;
    public SignupService(UnconfirmedUserRepo unconfirmedUserRepo,
                         ConfirmationTokenRepo confirmationTokenRepo,
                         UserRepo userRepo,
                         JavaMailSender mailSender,
                         BCryptPasswordEncoder passwordEncoder) {
        this.unconfirmedUserRepo = unconfirmedUserRepo;
        this.confirmationTokenRepo = confirmationTokenRepo;
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }
}
