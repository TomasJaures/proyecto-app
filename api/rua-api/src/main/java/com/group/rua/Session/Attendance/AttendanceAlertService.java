package com.group.rua.Session.Attendance;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.ClassesRepo;
import com.group.rua.Repositories.UserRepo;

import jakarta.mail.internet.MimeMessage;

@Service
public class AttendanceAlertService {

    private final OpenRouterService openRouterService;
    private final JavaMailSender mailSender;
    private final UserRepo userRepo;
    private final ClassesRepo classesRepo;
    private final String hostEmail = "ruaaplicacion@gmail.com";

    public AttendanceAlertService(OpenRouterService openRouterService, JavaMailSender mailSender, UserRepo userRepo, ClassesRepo classesRepo) {
        this.openRouterService = openRouterService;
        this.mailSender = mailSender;
        this.userRepo = userRepo;
        this.classesRepo = classesRepo;
    }

    public void generateAndSendAlert(Integer userId, Integer classId, Double percentage) {
        User student = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado."));

        ClassInfoDTO classInfo = classesRepo.findClassInfoByClassId(classId)
                .orElseThrow(() -> new RuntimeException("Información de la clase no encontrada."));

        String studentFullName = student.userName + " " + student.lastName1;

        // Generar el mensaje con IA
        String emailBody = openRouterService.generateAttendanceWarning(studentFullName, classInfo.getSubjectName(), percentage);

        // Enviar el correo
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(hostEmail);
            helper.setTo(student.mail);
            helper.setSubject("RUA - Alerta Temprana de Asistencia: " + classInfo.getSubjectName());

            helper.setText(emailBody.replace("\n", "<br>"), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo de alerta: " + e.getMessage());
        }
    }
}