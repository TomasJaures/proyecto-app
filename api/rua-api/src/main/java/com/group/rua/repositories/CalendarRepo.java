package com.group.rua.repositories;

import com.group.rua.entities.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para calendarios de usuario.
 */
public interface CalendarRepo extends JpaRepository<Calendar, Integer> {
}
