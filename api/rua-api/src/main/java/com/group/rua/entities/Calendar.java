package com.group.rua.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "calendar")
public class Calendar {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    public Integer calendarId;

}
