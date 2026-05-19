package com.group.rua.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.group.rua.Entities_Classes.User;

/**
 * Repositorio para usuario en la base de datos
 */
public interface UserRepo extends JpaRepository<User, Long> {}