package com.group.rua.Session.sign_up;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.group.rua.Entities_Classes.Calendar;
import com.group.rua.Entities_Classes.ConfirmationToken;
import com.group.rua.Entities_Classes.Program;
import com.group.rua.Entities_Classes.UnconfirmedUser;
import com.group.rua.Entities_Classes.User;
import com.group.rua.Exceptions.DuplicatedInUnconfirmedUsers;
import com.group.rua.Exceptions.DuplicatedInUsers;
import com.group.rua.Repositories.ConfirmationTokenRepo;
import com.group.rua.Repositories.UnconfirmedUserRepo;
import com.group.rua.Repositories.UserRepo;

import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
class SignupServiceTest {


    //Verificar si se creo usuario
    @Test
    void createUserTest() {

        // Arrange
        UnconfirmedUser user = createUnconfirmedUser();

        when(passwordEncoder.encode(anyString()))
                .thenReturn("hashedPassword");

        when(unconfirmedUserRepo.save(any(UnconfirmedUser.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        signupService.createUser(user);

        // Assert
        verify(unconfirmedUserRepo).save(any(UnconfirmedUser.class));
        verify(confirmationTokenRepo).save(any(ConfirmationToken.class));
    }

    @Test
    void verifyTokenTest_tokenExists() {
        //Arrange
        UnconfirmedUser user = new UnconfirmedUser();
        ConfirmationToken token = new ConfirmationToken();
        token.content = "abcd";
        token.unconfirmedUser = user;

        when(confirmationTokenRepo.findByContent(token.content))
            .thenReturn(Optional.of(token));
        
        
        //Act
        boolean isVerify = signupService.verifyToken(token.content);
        
        //Asserts
        assertTrue(isVerify);

        verify(unconfirmedUserRepo)
            .delete(token.unconfirmedUser);

        verify(userRepo).save(any(User.class));

    }

    @Test
    void verifyTokenTest_tokenDoesntExists() {

        // Arrange
        String token = "abcd";

        when(confirmationTokenRepo.findByContent(token))
                .thenReturn(Optional.empty());

        // Act
        boolean result = signupService.verifyToken(token);

        // Assert
        assertFalse(result);

        verify(unconfirmedUserRepo, never()).delete(any());
        verify(userRepo, never()).save(any());
    }

    @Test
    void promoteToConfirmedUserTest(){
        //Arrange
        UnconfirmedUser unconfirmedUser = createUnconfirmedUser();
        User user = createUser();


        //Act
        signupService.promoteToConfirmedUser(unconfirmedUser);

        //Asserts
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class); //Captor capturara ConfirmationToken
        verify(userRepo).save(captor.capture()); //Se busca confirmationTokenRepo.save y capture obtiene el argumento pasado
        User savedUser = captor.getValue(); //Obtenemos el argumento guardado

        assertEquals(user.mail, savedUser.mail);
        assertEquals(user.hashedPassword, savedUser.hashedPassword);
    }


    @Test
    void sendConfirmationEmailTest(){
        // Arrange
        String email = "hola@gmail.com";
        String token = "abcd-efgh";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act + Assert
        assertDoesNotThrow(() ->
            signupService.sendConfirmationEmail(email, token)
        );

        verify(mailSender).createMimeMessage();
        verify(mailSender).send(mimeMessage);
    }

    //Verificar que un token se guardo y se asocio al usuario correcto.
    @Test
    void saveConfirmationTokenTest(){
        
        //Arrange
        UnconfirmedUser user = new UnconfirmedUser();
        String tokenContent = "abcd-efgh";
        
        //Act
        signupService.saveConfirmationToken(user, tokenContent);;        
        
        //Assert
        ArgumentCaptor<ConfirmationToken> captor = ArgumentCaptor.forClass(ConfirmationToken.class); //Captor capturara ConfirmationToken
        verify(confirmationTokenRepo).save(captor.capture()); //Se busca confirmationTokenRepo.save y capture obtiene el argumento pasado
        ConfirmationToken savedToken = captor.getValue(); //Obtenemos el argumento guardado
        assertEquals(user, savedToken.unconfirmedUser);
        assertEquals(tokenContent, savedToken.content);

        
    }

    //Validar si hay duplicado en unconfirmedUsers tira ERROR
    //Validar si NO hay duplicado unconfirmedUsers NO tira ERROR
    //Validar si hay duplicado en users tira ERROR
    //Validar si NO hay duplicado users NO tira ERROR
    @Test
    void validateDuplicatedUnconfirmedUsers_throwError(){
        UnconfirmedUser user = new UnconfirmedUser();
        user.mail = "test@mail.com";

        when(unconfirmedUserRepo.findByMail("test@mail.com")).thenReturn(Optional.of(new UnconfirmedUser()));

        assertThrows(
                DuplicatedInUnconfirmedUsers.class,
                () -> signupService.validateDuplicatedUnconfirmedUsers(user)
        );
    }
    
    @Test
    void validateDuplicatedUnconfirmedUsers_normal(){
        UnconfirmedUser user = new UnconfirmedUser();
        user.mail = "test@mail.com";

        when(unconfirmedUserRepo.findByMail("test@mail.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> signupService.validateDuplicatedUnconfirmedUsers(user)
        );
    }
    
    @Test
    void validateDuplicatedUsers_throwError(){
        UnconfirmedUser user = new UnconfirmedUser();
        user.mail = "test@mail.com";

        when(userRepo.findByMail("test@mail.com")).thenReturn(Optional.of(new User()));

        assertThrows(
                DuplicatedInUsers.class,
                () -> signupService.validateDuplicatedUsers(user)
        );
    }
    
    @Test
    void validateDuplicatedUsers_normal(){
        UnconfirmedUser user = new UnconfirmedUser();
        user.mail = "test@mail.com";

        when(userRepo.findByMail("test@mail.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> signupService.validateDuplicatedUsers(user)
        );
    }

    //Funciones de apoyo, constantes y constructores

    UnconfirmedUser createUnconfirmedUser(){
        UnconfirmedUser unconfirmedUser = new UnconfirmedUser();
        unconfirmedUser.userName = "Tomas";
        unconfirmedUser.lastName1 = "Jaures";
        unconfirmedUser.lastName2 = "Painen";
        unconfirmedUser.mail = "t.jaures01@ufromail.cl";
        unconfirmedUser.hashedPassword = "a1b2c3d4";
        return unconfirmedUser;
    }

    User createUser(){
        User user = new User();
        user.userName = "Tomas";
        user.lastName1 = "Jaures";
        user.lastName2 = "Painen";
        user.mail = "t.jaures01@ufromail.cl";
        user.hashedPassword = "a1b2c3d4";
        user.userRole = "student";

        Calendar calendar = new Calendar();
        Program program = new Program();

        user.calendar = calendar;
        user.program = program;
        return user;
    }

    //Mockitos
    @Mock
    private UnconfirmedUserRepo unconfirmedUserRepo;
    @Mock
    private ConfirmationTokenRepo confirmationTokenRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private SignupService signupService;
}