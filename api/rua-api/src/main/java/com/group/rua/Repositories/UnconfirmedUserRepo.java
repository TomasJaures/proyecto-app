package com.group.rua.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.rua.Entities_Classes.UnconfirmedUser;

/**
 * Repositorio para usuarios pendientes de verificación de correo.
 */
public interface UnconfirmedUserRepo extends JpaRepository<UnconfirmedUser, Integer> {
    Optional<UnconfirmedUser> findByMail(String mail);
}