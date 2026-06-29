package com.group.rua.repositories;

import com.group.rua.entities.UnconfirmedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para usuarios pendientes de verificación de correo.
 */
public interface UnconfirmedUserRepo extends JpaRepository<UnconfirmedUser, Integer> {
    Optional<UnconfirmedUser> findByMail(String mail);
}

