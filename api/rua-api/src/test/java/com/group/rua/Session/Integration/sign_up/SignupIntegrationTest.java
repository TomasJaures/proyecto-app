package com.group.rua.Session.Integration.sign_up;

import com.group.rua.Repositories.UnconfirmedUserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class SignupIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UnconfirmedUserRepo unconfirmedUserRepo;

    // Caso de uso: Permitir registro de usuario
    @Test
    void createUser_EndpointDebeRegistrarUsuarioYRedirigir() throws Exception {
        String userJson = """
                {
                    "userName": "Juan",
                    "lastName1": "Perez",
                    "mail": "jperez@ufromail.cl",
                    "hashedPassword": "password123"
                }
                """;

        mockMvc.perform(post("/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost:1428/account/email_sended"));

        boolean existeEnBD = unconfirmedUserRepo.findByMail("jperez@ufromail.cl").isPresent();
        assertTrue(existeEnBD, "El usuario debería estar guardado en UnconfirmedUsers");
    }
}