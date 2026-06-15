package com.group.rua.Session.Attendance;

import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Repositories.AttendanceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepo attendanceRepo;

    public AttendanceService(AttendanceRepo attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    // listar alumnos presentes
    public List<Attendance> getPresentStudents(Integer classId) {
        return attendanceRepo.findByClassIdAndStatus(classId, "PRESENT");
    }

    // recopilar datos de asistencia manual
    public Attendance registerManualAttendance(Integer userId, Integer classId, String status) {

        // Buscamos si el docente ya había registrado al alumno antes (para actualizarlo)
        Attendance attendance = attendanceRepo.findByUserIdAndClassId(userId, classId)
                .orElse(new Attendance()); // Si no existe, creamos uno nuevo

        attendance.userId = userId;
        attendance.classId = classId;
        attendance.status = status;

        return attendanceRepo.save(attendance);
    }
}