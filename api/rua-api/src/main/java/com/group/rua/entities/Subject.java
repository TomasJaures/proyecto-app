package com.group.rua.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    public Integer subjectId;

    @Column(name = "subject_name")
    public String subjectName;

    @Column(name = "code")
    public String code;

}
