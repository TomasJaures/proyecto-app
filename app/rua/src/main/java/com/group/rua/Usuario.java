package com.group.rua;

public abstract class Usuario {
    private Long idUsuario;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String contrasena;

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
