package com.group.rua.Session.Integration.log_in;

import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Caso de uso: Permitir ingreso al sistema
    @Test
    void loginUser_ConCredencialesValidas_DebeRetornarOk() throws Exception {
        User usuario = new User();
        usuario.mail = "docente@ufrontera.cl";
        usuario.hashedPassword = passwordEncoder.encode("secreta123");
        // Si tienes más atributos requeridos en tu BD, agrégalos aquí
        usuario.userRole = "professor";
        userRepo.save(usuario);

        String loginJson = """
                {
                    "mail": "docente@ufrontera.cl",
                    "hashedPassword": "secreta123"
                }
                """;

        mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Inicio de sesión exitoso. ¡Bienvenido!"));
    }
}