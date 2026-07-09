package com.group.rua.repositories;

import com.group.rua.entities.Attendance;
import com.group.rua.entities.AttendanceId;
import com.group.rua.session.attendance.PresentStudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, AttendanceId> {
    

    @Query("""
        SELECT new com.group.rua.session.attendance.PresentStudentDTO(
            u.userId, u.userName, u.lastName1, u.lastName2, u.mail, a.status
        )
        FROM Attendance a
        JOIN User u ON a.userId = u.userId
        WHERE a.classId = :classId AND a.status = :status
    """)
    List<PresentStudentDTO> findPresentStudentsWithDetails(
        @Param("classId") Integer classId,
        @Param("status") String status
    );

    // para recopilar datos de asistencia manual (buscar si ya existe)
    Optional<Attendance> findByUserIdAndClassId(Integer userId, Integer classId);
}