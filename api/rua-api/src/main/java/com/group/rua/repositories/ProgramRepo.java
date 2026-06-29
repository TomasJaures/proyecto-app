package com.group.rua.repositories;

import com.group.rua.entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para programas académicos.
 */
public interface ProgramRepo extends JpaRepository<Program, Integer> {
}
