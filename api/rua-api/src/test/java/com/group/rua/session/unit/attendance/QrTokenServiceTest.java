package com.group.rua.session.unit.attendance;

import com.group.rua.entities.Attendance;
import com.group.rua.entities.Classes;
import com.group.rua.entities.QrToken;
import com.group.rua.repositories.ClassesRepo;
import com.group.rua.repositories.QrTokenRepo;
import com.group.rua.session.attendance.AttendanceService;
import com.group.rua.session.attendance.QrTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QrTokenServiceTest {

    @Mock
    private QrTokenRepo qrTokenRepo;

    @Mock
    private ClassesRepo classesRepo;

    @Mock
    private AttendanceService attendanceService;

    @InjectMocks
    private QrTokenService qrTokenService;

    // Verifica la generación exitosa de un token QR para una clase existente
    @Test
    void generateQrForClass_Success() {
        Classes mockClass = new Classes();
        mockClass.classId = 1;

        when(classesRepo.findById(1)).thenReturn(Optional.of(mockClass));
        when(qrTokenRepo.findByClassEntity_ClassId(1)).thenReturn(Optional.empty());

        QrToken savedToken = new QrToken();
        savedToken.content = "uuid-test";
        when(qrTokenRepo.save(any(QrToken.class))).thenReturn(savedToken);

        QrToken result = qrTokenService.generateQrForClass(1);

        assertNotNull(result);
        assertEquals("uuid-test", result.content);
        verify(qrTokenRepo, times(1)).save(any(QrToken.class));
    }

    // Verifica que un token válido registre la asistencia correctamente
    @Test
    void decodeQrAndRegisterAttendance_Success() {
        Classes mockClass = new Classes();
        mockClass.classId = 10;

        QrToken validToken = new QrToken();
        validToken.content = "valid-qr";
        validToken.expirationAt = LocalDateTime.now().plusMinutes(5); // Aún vigente
        validToken.classEntity = mockClass;

        Attendance mockAttendance = new Attendance();
        mockAttendance.status = "PRESENT";

        when(qrTokenRepo.findByContent("valid-qr")).thenReturn(Optional.of(validToken));
        when(attendanceService.registerManualAttendance("alumno@ufromail.cl", 10, "PRESENT")).thenReturn(mockAttendance);

        Attendance result = qrTokenService.decodeQrAndRegisterAttendance("valid-qr", "alumno@ufromail.cl");

        assertNotNull(result);
        assertEquals("PRESENT", result.status);
    }

    // Verifica que el sistema rechace un token QR cuya fecha de expiración ya pasó
    @Test
    void decodeQrAndRegisterAttendance_ExpiredToken_ThrowsException() {
        QrToken expiredToken = new QrToken();
        expiredToken.content = "expired-qr";
        expiredToken.expirationAt = LocalDateTime.now().minusMinutes(1); // Expiró hace 1 minuto

        when(qrTokenRepo.findByContent("expired-qr")).thenReturn(Optional.of(expiredToken));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                qrTokenService.decodeQrAndRegisterAttendance("expired-qr", "alumno@ufromail.cl")
        );

        assertEquals("El código QR ha expirado.", exception.getMessage());
        verify(attendanceService, never()).registerManualAttendance(anyString(), anyInt(), anyString());
    }
}