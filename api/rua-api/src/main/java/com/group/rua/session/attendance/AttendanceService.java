package com.group.rua.session.attendance;

import com.group.rua.entities.Attendance;
import com.group.rua.entities.User;
import com.group.rua.repositories.AttendanceRepo;
import com.group.rua.repositories.ClassesRepo;

import com.group.rua.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final ClassesRepo classesRepo;

    private final AttendanceRepo attendanceRepo;

    private final UserRepo userRepo;

    public AttendanceService(AttendanceRepo attendanceRepo, ClassesRepo classesRepo, UserRepo userRepo) {
        this.attendanceRepo = attendanceRepo;
        this.classesRepo = classesRepo;
        this.userRepo = userRepo;
    }

    public ClassInfoDTO getClassInfoWithDetails(Integer blockId) {
        return classesRepo.findClassInfoByBlockId(blockId)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró información detallada para el bloque: " + blockId));
    }

    public List<PresentStudentDTO> getPresentStudentsWithDetails(Integer classId) {
        return attendanceRepo.findPresentStudentsWithDetails(classId, "PRESENT");
    }

    // recopilar datos de asistencia manual
    public Attendance registerManualAttendance(String email, Integer classId, String status) {

        // Buscamos al usuario por su correo para obtener su ID
        User user = userRepo.findByMail(email)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un usuario con el correo: " + email));

        Integer userId = user.userId;

        // Buscamos si el docente ya había registrado al alumno antes (para actualizarlo)
        Attendance attendance = attendanceRepo.findByUserIdAndClassId(userId, classId)
                .orElse(new Attendance()); // Si no existe, creamos uno nuevo

        attendance.userId = userId;
        attendance.classId = classId;
        attendance.status = status;

        return attendanceRepo.save(attendance);
    }
}