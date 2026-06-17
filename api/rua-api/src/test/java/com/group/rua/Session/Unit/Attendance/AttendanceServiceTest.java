package com.group.rua.Session.Unit.Attendance;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Repositories.AttendanceRepo;
import com.group.rua.Session.Attendance.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class AttendanceServiceTest {

    @Mock
    private AttendanceRepo attendanceRepo;

    @InjectMocks
    private AttendanceService attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPresentStudents_ShouldReturnAttendanceList() {
        // Arrange
        Integer classId = 1;
        Attendance student1 = new Attendance();
        student1.userId = 100;
        student1.classId = classId;
        student1.status = "PRESENT";

        Attendance student2 = new Attendance();
        student2.userId = 101;
        student2.classId = classId;
        student2.status = "PRESENT";

        when(attendanceRepo.findByClassIdAndStatus(classId, "PRESENT")).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<Attendance> result = attendanceService.getPresentStudents(classId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("PRESENT", result.get(0).status);
        verify(attendanceRepo, times(1)).findByClassIdAndStatus(classId, "PRESENT");
    }

    @Test
    void registerManualAttendance_ShouldSaveAttendance() {
        // Arrange
        Integer userId = 100;
        Integer classId = 1;
        String status = "PRESENT";

        Attendance attendance = new Attendance();
        attendance.userId = userId;
        attendance.classId = classId;
        attendance.status = status;

        // Simulamos que no hay asistencia previa y luego guardamos
        when(attendanceRepo.findByUserIdAndClassId(userId, classId)).thenReturn(Optional.empty());
        when(attendanceRepo.save(any(Attendance.class))).thenReturn(attendance);

        // Act
        Attendance result = attendanceService.registerManualAttendance(userId, classId, status);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.userId);
        assertEquals(status, result.status);
        verify(attendanceRepo, times(1)).save(any(Attendance.class));
    }
}