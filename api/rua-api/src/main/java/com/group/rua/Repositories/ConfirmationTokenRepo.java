package com.group.rua.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.rua.Entities_Classes.ConfirmationToken;

/**
 * Repositorio para tokens de confirmación de correo.
 */
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Integer> {
    Optional<ConfirmationToken> findByContent(String content);
    void deleteByContent(String content);
}