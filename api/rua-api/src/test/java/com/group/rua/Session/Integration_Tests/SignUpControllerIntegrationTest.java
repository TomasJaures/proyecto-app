package com.group.rua.Session.Integration_Tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.group.rua.RuaConfig;
import com.group.rua.Entities_Classes.UnconfirmedUser;
import com.group.rua.Session.sign_up.SignupService;

@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerIntegrationTest {
    
    // account

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignupService signupService;

    @Test //create
    void createUser() throws Exception{
        String url = "/account/create";
        String headContent = RuaConfig.BACKEND_URL + "/account/email_sended";
        
        String userName = "Tomas";
        String lastName1 = "Jaures";
        String lastName2 = "Painen";
        String mail = "t.jaures01@ufromail.cl";
        String hashedPassword = "12345678";

        String json = String.format("""
        {
            "userName": "%s",
            "lastName1": "%s",
            "lastName2": "%s",
            "mail": "%s",
            "hashedPassword": "%s"
        }
        """, userName, lastName1, lastName2, mail, hashedPassword );
        
        mockMvc.perform(
                post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
            ).andExpectAll(
                status().isFound(),
                header().string("Location", headContent)
            );

        verify(signupService, times(1))
                .createUser(any(UnconfirmedUser.class));
    }

    @Test // verify_email
    void verifyEmail_success() throws Exception{
        //ARRANGE
        String url = "/account/verify_email";
        String headContent = RuaConfig.BACKEND_URL + "/account/confirmation_success";
        String token = "abcd-efgh";

        when(signupService.verifyToken(token)).thenReturn(true);

        //Act + Assert        
        mockMvc.perform(
            get(url).param("confirmationTokenString", token)
        ).andExpectAll(
            status().isFound(), 
            header().string("Location", headContent)
        ); 

    }

    @Test // verify_email
    void verifyEmail_fail() throws Exception{
        //Arrange
        String url = "/account/verify_email";
        String headContent = RuaConfig.BACKEND_URL + "/account/confirmation_fail";
        String token = "abcd-efgh";

        when(signupService.verifyToken(token)).thenReturn(false);

        //Act + Assert
        mockMvc.perform(
            get(url).param("confirmationTokenString", token)
        ).andExpectAll(
            status().isFound(), 
            header().string("Location", headContent)
        ); 

    }

    @Test // email_sended
    void notifyEmailSended() throws Exception{
        //ARRANGE
        String url = "/account/email_sended";
        String content = "Te hemos enviado un EMAIL!! Revisa tu correo de SPAM";

        //Act + Assert
        mockMvc.perform(get(url)).andExpectAll(
            status().isOk(), 
            content().string(content)
        ); 
    }

    @Test //confirmation_success
    void confirmation_success() throws Exception{
        
        String url = "/account/confirmation_success";
        String content = "Correo verificado correctamente! Puedes volver al Log In";
        
        mockMvc.perform(get(url)).andExpectAll(
            status().isOk(), 
            content().string(content)
        ); 
        
    }

    @Test //confirmation_fail
    void confirmation_fail() throws Exception{

        String url = "/account/confirmation_fail";
        String content = "Hubo un problema con tu correo :(";
        //Act + Assert
        mockMvc.perform(get(url)).andExpectAll(
                    status().isBadRequest(),
                    content().string(content)
        );
    }

}
