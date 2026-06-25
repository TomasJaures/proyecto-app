package com.group.rua.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.group.rua.Entities_Classes.QrToken;

/**
 * Repositorio para los tokens QR de asistencia a clases.
 */
public interface QrTokenRepo extends JpaRepository<QrToken, Integer> {
    Optional<QrToken> findByContent(String content);
    Optional<QrToken> findByClassEntity_ClassId(Integer classId);
}