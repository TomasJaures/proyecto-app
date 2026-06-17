package com.group.rua.Exceptions;

public class NoExistingRole extends RuntimeException {
    public NoExistingRole(String msg){
        super("\n ========= \n" + msg + " \n ========= \n");
    }
}
