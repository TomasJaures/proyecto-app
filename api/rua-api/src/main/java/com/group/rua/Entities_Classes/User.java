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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idUsuario;

    public String nombre;
    public String apellido1;
    public String apellido2;
    public String correo;
    public String contrasena;

    public String correo_verificado;
    public String token_verificacion;

    @Column(insertable = false, updatable = false)
    public LocalDateTime fecha_creacion;

    /**
     *
     * @param correo
     * @param contrasena
     */
    public boolean iniciarSesion(String correo, String contrasena) {
        // TODO - implement Usuario.iniciarSesion
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param correo
     * @param contrasena
     */
    public void registrarse(String nombre, String apellido1, String apellido2, String correo, String contrasena) {
        // TODO - implement Usuario.registrarse
        throw new UnsupportedOperationException();
    }

    public void correoValidacion() {
        // TODO - implement Usuario.correoValidacion
        throw new UnsupportedOperationException();
    }
}