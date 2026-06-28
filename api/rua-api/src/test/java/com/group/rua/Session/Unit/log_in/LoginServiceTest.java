package com.group.rua.Session.Unit.log_in;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.group.rua.Session.log_in.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.UnconfirmedUserRepo;
import com.group.rua.Repositories.UserRepo;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private UnconfirmedUserRepo unconfirmedUserRepo;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private LoginService loginService;

    //Ahora solo se debe verificar si el usuario esta en la BD para ver si esta verificado (Mas especificamente por la tabla Users).
    @Test
    void authenticate_Exitoso() {
        // Arrange
        User userInBD = new User(); //User in BD
        userInBD.hashedPassword = "a1b2c3d4";

        //Authenticate json
        String mail  = "test@ufromail.cl";
        String rawPass = "1234";

        //mockUser.correo_verificado = "true";

        when(userRepo.findByMail(mail)).thenReturn(Optional.of(userInBD));
        when(passwordEncoder.matches(rawPass, userInBD.hashedPassword)).thenReturn(true); //Retornar verdadero

        //Act
        boolean resultado = loginService.authenticate(mail, rawPass);

        // Assert -> verificar resultado
        assertTrue(resultado);
    }


    @Test
    void authenticate_FallaPorContrasenaIncorrecta() {
        //Arrange
        User userInBD = new User(); //User in BD
        userInBD.hashedPassword = "a1b2c3d4";

        //Authenticate json
        String mail  = "test@ufromail.cl";
        String rawPass = "1234";

        when(userRepo.findByMail(mail)).thenReturn(Optional.of(userInBD));
        when(passwordEncoder.matches(rawPass, userInBD.hashedPassword)).thenReturn(false); //Retornar falso

        //Act
        boolean resultado = loginService.authenticate(mail, rawPass);

        //Asserts
        assertFalse(resultado);
    }

    @Test
    void authenticate_FallaUsuarioNoExiste() {
        //Arrange
        when(userRepo.findByMail(anyString())).thenReturn(Optional.empty());

        //Act
        boolean resultado = loginService.authenticate("noexiste@ufromail.cl", "cualquierClave");


        //Assert
        assertFalse(resultado);
        verify(passwordEncoder, never()).matches(anyString(), anyString()); //No deberia pasar por el IF block
    }


}