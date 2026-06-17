package com.group.rua.Exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un correo que ya pertenece
 * a un usuario oficial y confirmado en el sistema.
 */

public class DuplicatedInUsers extends RuntimeException {
    public DuplicatedInUsers(String msg){
        super("\n ========= \n" + msg + " \n ========= \n");
    }
}
