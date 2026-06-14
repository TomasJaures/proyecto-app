package com.group.rua.Entities_Classes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


/**
 * He quitado temporalmente la herencia, no se usar herencia en Spring Boot y por lo que vi se tiene que hacer tomando en cuenta las tablas de la BD y sus conexiones, por eso de momento le quite la herencia.
 */

@Entity
@Table(name = "users")
public class User {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "user_name")
    public String userName;

    @Column(name = "last_name1")
    public String lastName1;

    @Column(name = "last_name2")
    public String lastName2;

    @Column(name = "mail")
    public String mail;

    @Column(name = "hashed_password")
    public String hashedPassword;

    @Column(name = "user_role")
    public String userRole;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "program_id")
    public Program program;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_id")
    public Calendar calendar;

}