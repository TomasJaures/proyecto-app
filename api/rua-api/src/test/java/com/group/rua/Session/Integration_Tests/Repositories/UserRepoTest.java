package com.group.rua.Session.Integration_Tests.Repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.group.rua.Entities_Classes.Calendar;
import com.group.rua.Entities_Classes.Program;
import com.group.rua.Entities_Classes.User;
import com.group.rua.Exceptions.NoExistingRole;
import com.group.rua.Repositories.UserRepo;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserRepoTest {
    
    @Autowired
    private UserRepo userRepo;

    @Test
    void shouldSaveUser(){
        User user = createUser();
        User savedUser = userRepo.save(user);
        
        assertNotNull(savedUser.userId);
    }

    User createUser(){
        User user = new User();
        user.userName = "Tomas";
        user.lastName1 = "Jaures";
        user.lastName2 = "Painen";
        user.mail = "t.jaures01@ufromail.cl";
        user.hashedPassword = "a1b2c3d4";
        user.userRole = asignRole(user.mail);

        Calendar calendar = new Calendar();
        Program program = new Program();

        user.calendar = calendar;
        user.program = program;
        return user;
    }

    String asignRole(String mail){
        String role = "";
        if (mail.contains("@ufromail.cl")) {
            role = "student";
        } else if (mail.contains("@ufrontera.cl")){
            role = "professor";
        } else {
            throw new NoExistingRole("el correo del usuario cuenta con un role designado");
        }
        return role;
    }

}
