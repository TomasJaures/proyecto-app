package com.group.rua.repositories;

import com.group.rua.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la gestión individual de los bloques horarios.
 */
public interface BlockRepo extends JpaRepository<Block, Integer> {
}