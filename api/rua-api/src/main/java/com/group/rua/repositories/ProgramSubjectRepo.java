package com.group.rua.repositories;

import com.group.rua.entities.ProgramSubject;
import com.group.rua.entities.ProgramSubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgramSubjectRepo extends JpaRepository<ProgramSubject, ProgramSubjectId> {
    // busca todos los registros intermedios filtrando por la ID del programa
    List<ProgramSubject> findById_ProgramId(Integer programId);
}