package com.group.rua.repositories;

import com.group.rua.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Repositorio para la gestión individual de los bloques horarios.
 */
public interface BlockRepo extends JpaRepository<Block, Integer> {
    @Query("SELECT b FROM Block b JOIN CalendarBlock cb ON b.blockId = cb.id.blockId WHERE cb.id.calendarId = :calendarId")
    List<Block> findBlocksByCalendarId(@Param("calendarId") Integer calendarId);
}