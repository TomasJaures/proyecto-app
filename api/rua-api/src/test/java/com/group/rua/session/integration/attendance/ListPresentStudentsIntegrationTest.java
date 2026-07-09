package com.group.rua.session.integration.attendance;

import com.group.rua.entities.Attendance;
import com.group.rua.entities.User;
import com.group.rua.repositories.AttendanceRepo;
import com.group.rua.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ListPresentStudentsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private UserRepo userRepo;

    // Caso de uso: Listar alumnos presentes
    @Test
    void getPresentStudents_EndpointShouldReturnAttendanceList() throws Exception {
        // Arrange
        Integer classIdTest = 11;

        // Crear y guardar usuarios
        User user1 = new User();
        user1.mail = "alumno1@ufromail.cl";
        user1.userName = "Juan";
        user1.lastName1 = "Perez";
        user1 = userRepo.save(user1);

        User user2 = new User();
        user2.mail = "alumno2@ufromail.cl";
        user2.userName = "Maria";
        user2.lastName1 = "Gomez";
        user2 = userRepo.save(user2);

        Attendance attendance1 = new Attendance();
        attendance1.userId = user1.userId;
        attendance1.classId = classIdTest;
        attendance1.status = "PRESENT";
        attendanceRepo.save(attendance1);

        Attendance attendance2 = new Attendance();
        attendance2.userId = user2.userId;
        attendance2.classId = classIdTest;
        attendance2.status = "PRESENT";
        attendanceRepo.save(attendance2);

        // Act & Assert
        mockMvc.perform(get("/api/attendance/class/" + classIdTest + "/present")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Validamos que retorne 2 alumnos
                .andExpect(jsonPath("$[0].userId").value(user1.userId))
                .andExpect(jsonPath("$[0].userName").value("Juan"))
                .andExpect(jsonPath("$[0].mail").value("alumno1@ufromail.cl"))
                .andExpect(jsonPath("$[1].userId").value(user2.userId))
                .andExpect(jsonPath("$[1].userName").value("Maria"))
                .andExpect(jsonPath("$[1].status").value("PRESENT"));
    }
}