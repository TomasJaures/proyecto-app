package com.group.rua.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.group.rua.Entities_Classes.Block;

/**
 * Repositorio para la gestión individual de los bloques horarios.
 */
public interface BlockRepo extends JpaRepository<Block, Integer> {
}