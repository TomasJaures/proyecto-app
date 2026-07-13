package com.group.rua.repositories;

import com.group.rua.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ModuleRepo extends JpaRepository<Module, Integer> {
    List<Module> findBySubjectId(Integer subjectId);
}