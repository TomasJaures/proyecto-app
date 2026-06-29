package com.group.rua.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "program")
public class Program {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    public Integer programId;

}
