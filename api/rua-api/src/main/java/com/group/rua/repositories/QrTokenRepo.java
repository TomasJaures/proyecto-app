package com.group.rua.repositories;

import com.group.rua.entities.QrToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para los tokens QR de asistencia a clases.
 */
public interface QrTokenRepo extends JpaRepository<QrToken, Integer> {
    Optional<QrToken> findByContent(String content);
    Optional<QrToken> findByClassEntity_ClassId(Integer classId);
}