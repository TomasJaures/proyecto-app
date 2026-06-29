package com.group.rua.session.unit.attendance;

import com.group.rua.entities.User;
import com.group.rua.repositories.ClassesRepo;
import com.group.rua.repositories.UserRepo;
import com.group.rua.session.attendance.AttendanceAlertService;
import com.group.rua.session.attendance.ClassInfoDTO;
import com.group.rua.session.attendance.OpenRouterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceAlertServiceTest {

    @Mock private OpenRouterService openRouterService;
    @Mock private JavaMailSender mailSender;
    @Mock private UserRepo userRepo;
    @Mock private ClassesRepo classesRepo;

    @InjectMocks private AttendanceAlertService attendanceAlertService;

    @Test
    void generateAndSendAlert_ShouldThrowException_WhenClassInfoIsMissing() {
        // Arrange
        Integer userId = 1;
        Integer invalidClassId = 500;
        User student = new User();
        student.mail = "alumno@ufromail.cl";

        when(userRepo.findById(Long.valueOf(userId))).thenReturn(Optional.of(student));
        when(classesRepo.findClassInfoByClassId(invalidClassId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                attendanceAlertService.generateAndSendAlert(userId, invalidClassId, 65.0)
        );

        verify(openRouterService, never()).generateAttendanceWarning(anyString(), anyString(), anyDouble());
    }

    @Test
    void generateAndSendAlert_ShouldWrapMessagingException_WhenMailServerFails() {
        // Arrange
        Integer userId = 1;
        Integer classId = 10;
        User student = new User();
        student.userName = "Juan";
        student.lastName1 = "Pérez";
        student.mail = "juan.perez@ufromail.cl";

        ClassInfoDTO mockClassInfo = mock(ClassInfoDTO.class);
        when(mockClassInfo.getSubjectName()).thenReturn("Estructuras de Datos");

        when(userRepo.findById(Long.valueOf(userId))).thenReturn(Optional.of(student));
        when(classesRepo.findClassInfoByClassId(classId)).thenReturn(Optional.of(mockClassInfo));
        when(openRouterService.generateAttendanceWarning(anyString(), anyString(), anyDouble()))
                .thenReturn("Cuerpo simulado por la IA");

        org.springframework.mail.javamail.JavaMailSenderImpl realMailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
        jakarta.mail.internet.MimeMessage realMimeMessage = realMailSender.createMimeMessage();

        when(mailSender.createMimeMessage()).thenReturn(realMimeMessage);

        doThrow(new org.springframework.mail.MailSendException("SMTP server connection timeout"))
                .when(mailSender).send(realMimeMessage);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                attendanceAlertService.generateAndSendAlert(userId, classId, 55.0)
        );

        assertTrue(exception.getMessage().contains("Error al enviar el correo de alerta"),
                "El mensaje debe contener el texto de error de negocio esperado");
    }
}