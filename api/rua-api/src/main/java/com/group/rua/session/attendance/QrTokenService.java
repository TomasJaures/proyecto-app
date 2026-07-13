package com.group.rua.session.attendance;

import com.group.rua.entities.*;
import com.group.rua.entities.Module;
import com.group.rua.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrTokenService {

    private final QrTokenRepo qrTokenRepo;
    private final ClassesRepo classesRepo;
    private final AttendanceService attendanceService;
    private final UserRepo userRepo;
    private final CalendarBlockRepository calendarBlockRepo;
    private final BlockRepo blockRepo;
    private final ModuleRepo moduleRepo;
    private final ProgramSubjectRepo programSubjectRepo;

    public QrTokenService(QrTokenRepo qrTokenRepo, ClassesRepo classesRepo, AttendanceService attendanceService,
                          UserRepo userRepo, CalendarBlockRepository calendarBlockRepo, BlockRepo blockRepo,
                          ModuleRepo moduleRepo, ProgramSubjectRepo programSubjectRepo) {
        this.qrTokenRepo = qrTokenRepo;
        this.classesRepo = classesRepo;
        this.attendanceService = attendanceService;
        this.userRepo = userRepo;
        this.calendarBlockRepo = calendarBlockRepo;
        this.blockRepo = blockRepo;
        this.moduleRepo = moduleRepo;
        this.programSubjectRepo = programSubjectRepo;
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

    // Decodificar código QR (Alumno) y validar información académica
    @Transactional
    public Map<String, Object> decodeQrAndRegisterAttendance(String content, String email) {
        QrToken qrToken = qrTokenRepo.findByContent(content)
                .orElseThrow(() -> new IllegalArgumentException("Código QR inválido o no encontrado."));

        if (LocalDateTime.now().isAfter(qrToken.expirationAt)) {
            throw new IllegalArgumentException("El código QR ha expirado.");
        }

        User user = userRepo.findByMail(email)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró usuario con el correo: " + email));

        Integer userId = user.userId;
        Integer calendarId = userId;
        Integer programId = userId;

        Classes targetClass = qrToken.classEntity;
        Integer blockId = targetClass.blockId;

        boolean operated = false;

        CalendarBlockId cbId = new CalendarBlockId();
        cbId.calendarId = calendarId;
        cbId.blockId = blockId;

        if (!calendarBlockRepo.existsById(cbId)) {
            operated = true;

            CalendarBlock cb = new CalendarBlock();
            cb.id = cbId;
            calendarBlockRepo.save(cb);

            Block block = blockRepo.findById(blockId)
                    .orElseThrow(() -> new IllegalArgumentException("Bloque no encontrado."));
            Module module = moduleRepo.findById(block.moduleId)
                    .orElseThrow(() -> new IllegalArgumentException("Módulo no encontrado."));
            Integer subjectId = module.subjectId;

            ProgramSubjectId psId = new ProgramSubjectId();
            psId.programId = programId;
            psId.subjectId = subjectId;

            if (!programSubjectRepo.existsById(psId)) {
                ProgramSubject ps = new ProgramSubject();
                ps.id = psId;
                programSubjectRepo.save(ps);
            }
        }

        //Registrar asistencia normalmente
        Attendance attendance = attendanceService.registerManualAttendance(email, targetClass.classId, "PRESENT");

        //Retornar el mapa con el formato solicitado
        return Map.of(
                "operated", operated,
                "attendance", attendance
        );
    }
}