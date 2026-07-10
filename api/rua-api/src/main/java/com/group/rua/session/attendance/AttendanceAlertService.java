package com.group.rua.session.attendance;

import com.group.rua.entities.User;
import com.group.rua.repositories.ClassesRepo;
import com.group.rua.repositories.UserRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class AttendanceAlertService {

    private final OpenRouterService openRouterService;
    private final JavaMailSender mailSender;
    private final UserRepo userRepo;
    private final ClassesRepo classesRepo;
    private static final String HOST_EMAIL = "ruaaplicacion@gmail.com";

    public AttendanceAlertService(OpenRouterService openRouterService, JavaMailSender mailSender, UserRepo userRepo, ClassesRepo classesRepo) {
        this.openRouterService = openRouterService;
        this.mailSender = mailSender;
        this.userRepo = userRepo;
        this.classesRepo = classesRepo;
    }

    public void generateAndSendAlert(Integer userId, Integer classId, Double percentage) {
        User student = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado."));

        ClassInfoDTO classInfo = classesRepo.findClassInfoByClassId(classId)
                .orElseThrow(() -> new IllegalArgumentException("Información de la clase no encontrada."));

        String studentFullName = student.userName + " " + student.lastName1;

        // Generar el mensaje con IA
        String emailBody = openRouterService.generateAttendanceWarning(studentFullName, classInfo.getSubjectName(), percentage);

        // Enviar el correo
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(HOST_EMAIL);
            helper.setTo(student.mail);
            helper.setSubject("RUA - Alerta Temprana de Asistencia: " + classInfo.getSubjectName());

            helper.setText(emailBody.replace("\n", "<br>"), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Error al enviar el correo de alerta: " + e.getMessage());
        }
    }
}