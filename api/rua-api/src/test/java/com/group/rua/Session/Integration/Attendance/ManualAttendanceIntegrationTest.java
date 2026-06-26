package com.group.rua.Session.Integration.Attendance;

import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Repositories.AttendanceRepo;
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
    private AttendanceRepo attendanceRepo;

    // Caso de uso: Recopilar datos de asistencia manual
    @Test
    void registerManualAttendance_EndpointShouldSaveInDB() throws Exception {
        Integer userIdTest = 99;
        Integer classIdTest = 100;
        String statusTest = "PRESENT";

        mockMvc.perform(post("/api/attendance/manual")
                        .param("userId", userIdTest.toString())
                        .param("classId", classIdTest.toString())
                        .param("status", statusTest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userIdTest))
                .andExpect(jsonPath("$.status").value(statusTest));

        var attendanceOpt = attendanceRepo.findByUserIdAndClassId(userIdTest, classIdTest);

        assertTrue(attendanceOpt.isPresent(), "The attendance record should exist in the database");

        Attendance savedAttendance = attendanceOpt.get();
        assertEquals("PRESENT", savedAttendance.status);
    }
}