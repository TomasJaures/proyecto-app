package com.group.rua.Exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un correo que ya se encuentra
 * en la base de datos como un usuario pendiente de confirmación.
 */

public class DuplicatedInUnconfirmedUsers extends RuntimeException {
    public DuplicatedInUnconfirmedUsers(String msg){
        super("\n ========= \n" + msg + " \n ========= \n");
    }
}