package com.group.rua.repositories;

import com.group.rua.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para usuario en la base de datos
 */
public interface UserRepo extends JpaRepository<User, Long> {
   Optional<User> findByMail(String mail); //para buscar un usuario usando su correo
}