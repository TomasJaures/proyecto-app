package com.group.rua.Session.Integration_Tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.group.rua.Session.log_in.LoginService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Anotacion para pruebas de integracion
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIntegrationTest {
    

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    void loginUser_succes() throws Exception{
        
        //Arrange
        String mail = "test@ufromail.cl";
        String hashedPassword = "a1b2c3d4";

        String json = String.format("""
        {
            "mail":"%s",
            "hashedPassword":"%s"
        }
        """, mail, hashedPassword);

        when(loginService.authenticate(mail, hashedPassword)).thenReturn(true);
        
        //Act + Assert
        mockMvc.perform(post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Inicio de sesión exitoso. ¡Bienvenido!"));
    }

    @Test
    void loginUser_fail() throws Exception{
        String mail = "noexiste@ufromail.cl";
        String hashedPassword = "a1b2c3d4";

        String json = String.format("""
        {
            "mail":"%s",
            "hashedPassword":"%s"
        }
        """, mail, hashedPassword);

        when(loginService.authenticate(mail, hashedPassword)).thenReturn(false);

        mockMvc.perform(post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales inválidas o correo no verificado."));
    }
}
