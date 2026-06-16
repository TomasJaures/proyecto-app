package com.group.rua.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.group.rua.Entities_Classes.Attendance;
import com.group.rua.Entities_Classes.AttendanceId;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, AttendanceId> {


    
    // para listar alumnos presentes
    List<Attendance> findByClassIdAndStatus(Integer classId, String status);

    // para recopilar datos de asistencia manual (buscar si ya existe)
    Optional<Attendance> findByUserIdAndClassId(Integer userId, Integer classId);
}