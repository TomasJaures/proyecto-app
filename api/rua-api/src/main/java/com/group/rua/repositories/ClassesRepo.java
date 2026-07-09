package com.group.rua.repositories;

import com.group.rua.entities.Classes;
import com.group.rua.session.attendance.ClassInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface ClassesRepo extends JpaRepository<Classes, Integer> {

    Optional<Classes> findByBlockId(Integer id);


    @Query("SELECT new com.group.rua.session.attendance.ClassInfoDTO(" +
           "b.blockId, b.blockState, c.classId, c.isAnulled, m.num, s.subjectName, s.code, b.weekDay, b.startHour, b.endHour, c.classDate) " +
           "FROM Classes c " +
           "JOIN Block b ON c.blockId = b.blockId " +
           "JOIN Module m ON b.moduleId = m.moduleId " +
           "JOIN Subject s ON m.subjectId = s.subjectId " +
           "WHERE c.blockId = :blockId")
    Optional<ClassInfoDTO> findClassInfoByBlockId(@Param("blockId") Integer blockId);

    @Query("SELECT new com.group.rua.session.attendance.ClassInfoDTO(" +
            "b.blockId, b.blockState, c.classId, c.isAnulled, m.num, s.subjectName, s.code, b.weekDay, b.startHour, b.endHour, c.classDate) " +
            "FROM Classes c " +
            "JOIN Block b ON c.blockId = b.blockId " +
            "JOIN Module m ON b.moduleId = m.moduleId " +
            "JOIN Subject s ON m.subjectId = s.subjectId " +
            "WHERE c.classId = :classId")
    Optional<ClassInfoDTO> findClassInfoByClassId(@Param("classId") Integer classId);

    @Query("SELECT new com.group.rua.session.attendance.ClassInfoDTO(" +
            "b.blockId, b.blockState, c.classId, c.isAnulled, m.num, s.subjectName, s.code, b.weekDay, b.startHour, b.endHour, c.classDate) " +
            "FROM Classes c " +
            "JOIN Block b ON c.blockId = b.blockId " +
            "JOIN CalendarBlock cb ON b.blockId = cb.id.blockId " +
            "JOIN Module m ON b.moduleId = m.moduleId " +
            "JOIN Subject s ON m.subjectId = s.subjectId " +
            "WHERE cb.id.calendarId = :calendarId " +
            "AND c.classDate BETWEEN :startOfWeek AND :endOfWeek")
    List<ClassInfoDTO> findAllClassesByCalendarIdAndWeek(
            @Param("calendarId") Integer calendarId,
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek);
}
