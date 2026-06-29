package com.group.rua.session.integration.log_in;

import com.group.rua.entities.Calendar;
import com.group.rua.entities.Program;
import com.group.rua.entities.User;
import com.group.rua.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @BeforeEach
    void setUp() {
        userRepo.deleteAll();
    }

    // Caso de uso: Permitir ingreso al sistema
    @Test
    void permitirIngresoAlSistema_ConCredencialesValidas_RetornaOk() throws Exception {

        // Arrange
        User usuario = new User();
        usuario.mail = "docente@ufrontera.cl";
        usuario.hashedPassword = passwordEncoder.encode("secreta123");
        usuario.userRole = "professor";

        // Crear Program y Calendar SIN asignar IDs (Hibernate los generará)
        Program program = new Program();
        Calendar calendar = new Calendar();

        usuario.program = program;
        usuario.calendar = calendar;

        userRepo.save(usuario);

        String requestBody = """
                {
                    "mail": "docente@ufrontera.cl",
                    "hashedPassword": "secreta123"
                }
                """;

        mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("professor"))
                .andExpect(jsonPath("$.id").exists());
    }
}