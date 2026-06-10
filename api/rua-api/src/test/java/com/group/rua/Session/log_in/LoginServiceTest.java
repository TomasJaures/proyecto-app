package com.group.rua.Session.log_in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.group.rua.Repositories.UserRepo;
import com.group.rua.Entities_Classes.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @Test
    void authenticate_Exitoso() {
        // aqui preparamos los datos
        User mockUser = new User();
        mockUser.correo = "test@ufromail.cl";
        mockUser.contrasena = "hash123";
        mockUser.correo_verificado = "true";

        when(userRepo.findByCorreo("test@ufromail.cl")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("miPasswordPlano", "hash123")).thenReturn(true);

        // Ejecutar método
        boolean resultado = loginService.authenticate("test@ufromail.cl", "miPasswordPlano");

        // Assert -> verificar resultado
        assertTrue(resultado);
    }

    @Test
    void authenticate_FallaPorCorreoNoVerificado() {
        User mockUser = new User();
        mockUser.correo = "test@ufromail.cl";
        mockUser.contrasena = "hash123";
        mockUser.correo_verificado = null; // o "false"

        when(userRepo.findByCorreo("test@ufromail.cl")).thenReturn(Optional.of(mockUser));

        boolean resultado = loginService.authenticate("test@ufromail.cl", "miPasswordPlano");

        assertFalse(resultado);
        // Verificamos que nunca se llame al passwordEncoder porque el correo no está verificado
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void authenticate_FallaPorContrasenaIncorrecta() {
        User mockUser = new User();
        mockUser.correo = "test@ufromail.cl";
        mockUser.contrasena = "hash123";
        mockUser.correo_verificado = "true";

        when(userRepo.findByCorreo("test@ufromail.cl")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("passwordEquivocado", "hash123")).thenReturn(false);

        boolean resultado = loginService.authenticate("test@ufromail.cl", "passwordEquivocado");

        assertFalse(resultado);
    }

    @Test
    void authenticate_FallaUsuarioNoExiste() {
        when(userRepo.findByCorreo("noexiste@ufromail.cl")).thenReturn(Optional.empty());

        boolean resultado = loginService.authenticate("noexiste@ufromail.cl", "cualquierClave");

        assertFalse(resultado);
    }
}
