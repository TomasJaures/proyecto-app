package com.group.rua.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad para la tabla join program_subjects (relación N:M entre program y subjects).
 * Usa @EmbeddedId con clave compuesta (program_id, subject_id).
 */
@Entity
@Table(name = "program_subjects")
public class ProgramSubject {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @EmbeddedId
    public ProgramSubjectId id;

}
