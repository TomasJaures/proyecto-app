package com.group.rua.session.integration.attendance;

import com.group.rua.repositories.UserRepo;
import com.group.rua.repositories.AttendanceRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ManualAttendanceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    // Caso de uso: Recopilar datos de asistencia manual
    @Test
    void registerManualAttendance_EndpointShouldSaveInDB() throws Exception {
        String emailTest = "test@ufromail.cl";
        Integer classIdTest = 100;
        String statusTest = "PRESENT";

        // guardar un usuario falso en la BD de pruebas para que el servicio lo encuentre
        com.group.rua.entities.User testUser = new com.group.rua.entities.User();
        testUser.mail = emailTest;
        testUser.userName = "Alumno Prueba";
        testUser = userRepo.save(testUser);

        mockMvc.perform(post("/api/attendance/manual")
                        .param("email", emailTest) // ¡Enviamos el correo, no el userId!
                        .param("classId", classIdTest.toString())
                        .param("status", statusTest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(testUser.userId)) // Validamos con el ID real
                .andExpect(jsonPath("$.status").value(statusTest));

        var attendanceOpt = attendanceRepo.findByUserIdAndClassId(testUser.userId, classIdTest);
        assertTrue(attendanceOpt.isPresent(), "The attendance record should exist in the database");

        com.group.rua.entities.Attendance savedAttendance = attendanceOpt.get();
        assertEquals("PRESENT", savedAttendance.status);
    }
}