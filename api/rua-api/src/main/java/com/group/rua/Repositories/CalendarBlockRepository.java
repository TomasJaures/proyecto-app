package com.group.rua.Repositories;

import com.group.rua.Session.Calendar.CalendarBlockDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.group.rua.Entities_Classes.CalendarBlock;
import com.group.rua.Entities_Classes.CalendarBlockId;

import java.util.List;

public interface CalendarBlockRepository extends JpaRepository<CalendarBlock, CalendarBlockId> {

    /**
     * Devuelve todos los bloques asociados a un calendario,
     * enriquecidos con datos de módulo y asignatura.
     * * [RESUELTO]: Se excluyen los bloques con estado REMOVED.
     *
     * La query replica la consulta SQL de referencia usando JPQL
     * sobre los @Column mapeados como Integer (sin relaciones JPA).
     *
     * Nota: se usan los nombres de campo Java (camelCase), no los
     * nombres de columna SQL.
     */
    @Query("""
        SELECT new com.group.rua.Session.Calendar.CalendarBlockDTO(
            cb.id.calendarId,
            b.blockId,
            b.weekDay,
            b.startHour,
            b.endHour,
            b.blockState,
            m.moduleId,
            m.num,
            s.subjectId,
            s.subjectName,
            s.code
        )
        FROM CalendarBlock cb
        JOIN Block b ON cb.id.blockId = b.blockId
        JOIN Module m ON b.moduleId   = m.moduleId
        JOIN Subject s ON m.subjectId = s.subjectId
        WHERE cb.id.calendarId = :calendarId
        AND b.blockState != com.group.rua.Entities_Classes.Block$BlockState.REMOVED
    """)
    List<CalendarBlockDTO> findBlocksByCalendarId(@Param("calendarId") Integer calendarId);

    //blocks.block_id
    //blocks.block_state
    //classes.class_id
    //classes.is_anulled
    //modules.num
    //subjects.subject_name
    //subjects.code
}