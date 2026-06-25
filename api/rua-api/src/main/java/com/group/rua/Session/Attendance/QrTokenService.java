package com.group.rua.Session.Attendance;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Entities_Classes.Classes;
import com.group.rua.Entities_Classes.QrToken;
import com.group.rua.Repositories.AttendanceRepo;
import com.group.rua.Repositories.ClassesRepo;
import com.group.rua.Repositories.QrTokenRepo;

@Service
public class QrTokenService {

    private final QrTokenRepo qrTokenRepo;
    private final ClassesRepo classesRepo;
    private final AttendanceService attendanceService;

    public QrTokenService(QrTokenRepo qrTokenRepo, ClassesRepo classesRepo, AttendanceService attendanceService) {
        this.qrTokenRepo = qrTokenRepo;
        this.classesRepo = classesRepo;
        this.attendanceService = attendanceService;
    }

    //Lanzar nuevo código QR (Docente)

    public QrToken generateQrForClass(Integer classId) {
        Classes targetClass = classesRepo.findById(classId)
                .orElseThrow(() -> new RuntimeException("La clase especificada no existe."));

        Optional<QrToken> existingToken = qrTokenRepo.findByClassEntity_ClassId(classId);
        existingToken.ifPresent(qrTokenRepo::delete);

        QrToken newToken = new QrToken();
        newToken.content = UUID.randomUUID().toString();
        newToken.createdAt = LocalDateTime.now();
        newToken.expirationAt = LocalDateTime.now().plusMinutes(3);
        newToken.classEntity = targetClass;

        return qrTokenRepo.save(newToken);
    }

    //Decodificar código QR (Alumno)

    public Attendance decodeQrAndRegisterAttendance(String content, Integer userId) {
        QrToken qrToken = qrTokenRepo.findByContent(content)
                .orElseThrow(() -> new RuntimeException("Código QR inválido o no encontrado."));

        if (LocalDateTime.now().isAfter(qrToken.expirationAt)) {
            throw new RuntimeException("El código QR ha expirado.");
        }

        return attendanceService.registerManualAttendance(userId, qrToken.classEntity.classId, "PRESENT");
    }
}