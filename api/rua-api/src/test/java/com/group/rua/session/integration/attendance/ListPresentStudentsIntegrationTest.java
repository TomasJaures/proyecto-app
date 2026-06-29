package com.group.rua.session.integration.attendance;

import com.group.rua.entities.Attendance;
import com.group.rua.repositories.AttendanceRepo;
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

    // Caso de uso: Listar alumnos presentes
    @Test
    void getPresentStudents_EndpointShouldReturnAttendanceList() throws Exception {
        Integer classIdTest = 50;

        Attendance student1 = new Attendance();
        student1.userId = 10;
        student1.classId = classIdTest;
        student1.status = "PRESENT";
        attendanceRepo.save(student1);

        Attendance student2 = new Attendance();
        student2.userId = 11;
        student2.classId = classIdTest;
        student2.status = "PRESENT";
        attendanceRepo.save(student2);

        mockMvc.perform(get("/api/attendance/class/" + classIdTest + "/present")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].userId").value(10))
                .andExpect(jsonPath("$[1].userId").value(11));
    }
}