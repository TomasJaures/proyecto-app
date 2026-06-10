package com.group.rua.Entities_Classes;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * He quitado temporalmente la herencia, no se usar herencia en Spring Boot y por lo que vi se tiene que hacer tomando en cuenta las tablas de la BD y sus conexiones, por eso de momento le quite la herencia. 
 */

@Entity
@Table(name = "usuarios") //Tabla usuarios en la BD;
public class User {

    /**
     * [!!!] No hay motivos por lo que todos los atributos sean publicos, unicamente es para que la seccion no tenga 200+ lineas de Getters y Setters
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idUsuario;

    public String nombre;
    public String apellido1;
    public String apellido2;
    public String correo;
    public String contrasena;

    public String correo_verificado;

    /**
     * token_verificacion me daba problemas por el metodo de UserRepo.java, Spring Boot solo aceptaba camelCase, de ahi a mas nada.
     */
    public String tokenConfirmation;

    @Column(insertable = false, updatable = false)
    public LocalDateTime fecha_creacion;

}