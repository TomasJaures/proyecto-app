package com.group.rua.session.unit.attendance;

import com.group.rua.entities.Attendance;
import com.group.rua.repositories.AttendanceRepo;
import com.group.rua.repositories.ClassesRepo;
import com.group.rua.session.attendance.AttendanceService;
import com.group.rua.session.attendance.ClassInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceTest {

    @Mock
    private AttendanceRepo attendanceRepo;

    @Mock
    private ClassesRepo classesRepo;

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

    @Test
    void registerManualAttendance_ShouldCreateNewRecord_WhenNoneExists() {
        // Arrange
        Integer userIdTest = 50;
        Integer classIdTest = 101;
        String statusTest = "LATE";

        when(attendanceRepo.findByUserIdAndClassId(userIdTest, classIdTest))
                .thenReturn(Optional.empty());

        when(attendanceRepo.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Attendance result = attendanceService.registerManualAttendance(userIdTest, classIdTest, statusTest);

        // Assert
        assertNotNull(result, "El registro de asistencia no debe ser nulo");
        assertEquals(userIdTest, result.userId, "El ID del usuario debe coincidir");
        assertEquals(classIdTest, result.classId, "El ID de la clase debe coincidir");
        assertEquals(statusTest, result.status, "El estado de asistencia debe ser el proporcionado");

        verify(attendanceRepo, times(1)).findByUserIdAndClassId(userIdTest, classIdTest);
        verify(attendanceRepo, times(1)).save(any(Attendance.class));
    }

    @Test
    void getClassInfoWithDetails_ShouldThrowException_WithCorrectMessage_WhenNotFound() {
        // Arrange
        Integer invalidBlockId = 999;
        when(classesRepo.findClassInfoByBlockId(invalidBlockId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                attendanceService.getClassInfoWithDetails(invalidBlockId)
        );

        assertTrue(exception.getMessage().contains(invalidBlockId.toString()),
                "El mensaje de error debe incluir el ID del bloque que falló para facilitar el debugging");
    }

    @Test
    void getClassInfoWithDetails_ShouldReturnExactDto_WhenFound() {
        // Arrange
        // Nota: Si ClassInfoDTO es un record o tiene constructor, se le puede pasar datos falsos
        ClassInfoDTO mockDto = mock(ClassInfoDTO.class);
        Integer validBlockId = 10;

        when(classesRepo.findClassInfoByBlockId(validBlockId)).thenReturn(Optional.of(mockDto));

        // Act
        ClassInfoDTO result = attendanceService.getClassInfoWithDetails(validBlockId);

        // Assert
        assertNotNull(result);
        assertEquals(mockDto, result, "El servicio debe retornar el mismo DTO proveído por el repositorio");
        verify(classesRepo, times(1)).findClassInfoByBlockId(validBlockId);
    }
}