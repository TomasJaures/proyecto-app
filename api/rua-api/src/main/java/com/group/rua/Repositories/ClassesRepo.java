package com.group.rua.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.group.rua.Entities_Classes.Classes;
import com.group.rua.Session.Attendance.ClassInfoDTO;

public interface ClassesRepo extends JpaRepository<Classes, Integer> {

    Optional<Classes> findByBlockId(Integer id);


    @Query("SELECT new com.group.rua.Session.Attendance.ClassInfoDTO(" +
           "b.blockId, b.blockState, c.classId, c.isAnulled, m.num, s.subjectName, s.code) " +
           "FROM Classes c " +
           "JOIN Block b ON c.blockId = b.blockId " +
           "JOIN Module m ON b.moduleId = m.moduleId " +
           "JOIN Subject s ON m.subjectId = s.subjectId " +
           "WHERE c.blockId = :blockId")
    Optional<ClassInfoDTO> findClassInfoByBlockId(@Param("blockId") Integer blockId);

    @Query("SELECT new com.group.rua.Session.Attendance.ClassInfoDTO(" +
            "b.blockId, b.blockState, c.classId, c.isAnulled, m.num, s.subjectName, s.code) " +
            "FROM Classes c " +
            "JOIN Block b ON c.blockId = b.blockId " +
            "JOIN Module m ON b.moduleId = m.moduleId " +
            "JOIN Subject s ON m.subjectId = s.subjectId " +
            "WHERE c.classId = :classId")
    Optional<ClassInfoDTO> findClassInfoByClassId(@Param("classId") Integer classId);
}
