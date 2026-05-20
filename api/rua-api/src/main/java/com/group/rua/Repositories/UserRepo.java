package com.group.rua.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.group.rua.Entities_Classes.User;

/**
 * Repositorio para usuario en la base de datos
 */
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByTokenConfirmation(String TokenConfirmation);
    Optional<User> deleteByTokenConfirmation(String TokenConfirmation);
}