package com.group.rua.repositories;

import com.group.rua.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para tokens de confirmación de correo.
 */
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Integer> {
    Optional<ConfirmationToken> findByContent(String content);
    void deleteByContent(String content);
}