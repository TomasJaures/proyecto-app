package com.group.rua.Entities_Classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "modules")
public class Module {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    public Integer moduleId;

    // FK N:1 hacia subjects — mapeada como Integer (convención del proyecto)
    @Column(name = "subject_id")
    public Integer subjectId;

    // Número de módulo (1 a 9)
    @Column(name = "num")
    public Integer num;

}
