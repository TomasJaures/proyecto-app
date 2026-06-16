package com.group.rua.Entities_Classes;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Clave primaria compuesta para la tabla program_subjects (N:M entre program y subjects).
 * Requiere implementar equals() y hashCode() por convención de JPA con @EmbeddedId.
 */
@Embeddable
public class ProgramSubjectId implements Serializable {

    @Column(name = "program_id")
    public Integer programId;

    @Column(name = "subject_id")
    public Integer subjectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramSubjectId)) return false;
        ProgramSubjectId that = (ProgramSubjectId) o;
        return Objects.equals(programId, that.programId) &&
               Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programId, subjectId);
    }

}
