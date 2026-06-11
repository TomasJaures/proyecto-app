/*
package com.group.rua.Session.sign_up;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.UserRepo;

import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
class SignupServiceTest {

    @Mock
    private UserRepo userTable;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private SignupService signupService;

    @Test
    void createUser_UsuarioNuevoExitoso() throws Exception {
        // Arrange ** tengo q revisaaar**
        User newUser = new User();
        newUser.correo = "nuevo@ufromail.cl";
        newUser.contrasena = "clavePlana";

        when(userTable.findByCorreo("nuevo@ufromail.cl")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("clavePlana")).thenReturn("claveEncriptada");

        // Mockeamos el mensaje para que JavaMailSender no arroje NullPointerException
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        signupService.createUser(newUser);

        // Assert
        assertEquals("claveEncriptada", newUser.contrasena);
        assertNotNull(newUser.tokenConfirmation);

        // verificamos que se haya guardado en la BD
        verify(userTable, times(1)).save(newUser);
        // verificamos que se intentó enviar el correo
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void createUser_FallaCorreoYaExiste() {
        // Arrange
        User userExistente = new User();
        userExistente.correo = "registrado@ufromail.cl";

        when(userTable.findByCorreo("registrado@ufromail.cl")).thenReturn(Optional.of(userExistente));

        // Act & Assert (Verificamos que lance la excepción)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            signupService.createUser(userExistente);
        });

        assertEquals("El correo ya se encuentra registrado.", exception.getMessage());
        // Verificamos que NUNCA se guardó nada en la base de datos
        verify(userTable, never()).save(any(User.class));
    }

    @Test
    void verifyToken_TokenValido() {
        User user = new User();
        user.tokenConfirmation = "token-valido-123";

        when(userTable.findByTokenConfirmation("token-valido-123")).thenReturn(Optional.of(user));

        boolean resultado = signupService.verifyToken("token-valido-123");

        assertTrue(resultado);
        assertEquals("true", user.correo_verificado);
        assertNull(user.tokenConfirmation);
        verify(userTable, times(1)).save(user);
    }
}

*/