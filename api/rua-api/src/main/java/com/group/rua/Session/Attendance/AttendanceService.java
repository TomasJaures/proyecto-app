package com.group.rua.Session.Attendance;

import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Entities_Classes.Classes;
import com.group.rua.Entities_Classes.User;
import com.group.rua.Repositories.AttendanceRepo;
import com.group.rua.Repositories.ClassesRepo;
import com.group.rua.Repositories.UserRepo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private final ClassesRepo classesRepo;

    private final AttendanceRepo attendanceRepo;

    public AttendanceService(AttendanceRepo attendanceRepo, ClassesRepo classesRepo) {
        this.attendanceRepo = attendanceRepo;
        this.classesRepo = classesRepo;
    }

    // listar alumnos presentes
    public List<Attendance> getPresentStudents(Integer classId) {
        return attendanceRepo.findByClassIdAndStatus(classId, "PRESENT");
        
    }

    public ClassInfoDTO getClassInfoWithDetails(Integer blockId) {
        return classesRepo.findClassInfoByBlockId(blockId)
            .orElseThrow(() -> new RuntimeException("No se encontró información detallada para el bloque: " + blockId));
    }

    public Classes getLastClassAsignedToBlock(Integer BlockId){
        Optional<Classes> classesOpt = classesRepo.findByBlockId(BlockId);

        if(classesOpt.isPresent()){
            Classes c = classesOpt.get();
            return c;
        } else {
            throw new RuntimeException("No hay clase con esa ID"); //FIXME: agregar expection personalizada
        }
    }

    public List<PresentStudentDTO> getPresentStudentsWithDetails(Integer classId) {
        return attendanceRepo.findPresentStudentsWithDetails(classId, "PRESENT");
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