package com.group.rua.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.rua.Entities_Classes.Calendar;

/**
 * Repositorio para calendarios de usuario.
 */
public interface CalendarRepo extends JpaRepository<Calendar, Integer> {
}