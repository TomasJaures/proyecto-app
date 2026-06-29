package com.group.rua.exceptions;

public class NoExistingRole extends RuntimeException {
    public NoExistingRole(String msg){
        super("\n ========= \n" + msg + " \n ========= \n");
    }
}
