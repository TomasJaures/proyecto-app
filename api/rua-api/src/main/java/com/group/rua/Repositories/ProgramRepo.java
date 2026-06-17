package com.group.rua.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.rua.Entities_Classes.Program;

/**
 * Repositorio para programas académicos.
 */
public interface ProgramRepo extends JpaRepository<Program, Integer> {
}
