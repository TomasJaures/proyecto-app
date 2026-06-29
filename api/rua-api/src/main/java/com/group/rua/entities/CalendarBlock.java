package com.group.rua.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad para la tabla join calendar_blocks (relación N:M entre calendar y blocks).
 * Usa @EmbeddedId con clave compuesta (calendar_id, block_id).
 */
@Entity
@Table(name = "calendar_blocks")
public class CalendarBlock {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @EmbeddedId
    public CalendarBlockId id;

}
