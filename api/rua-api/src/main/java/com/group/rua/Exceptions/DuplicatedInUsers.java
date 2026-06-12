package com.group.rua.Exceptions;

public class DuplicatedInUsers extends RuntimeException {
    public DuplicatedInUsers(String msg){
        super("\n ========= \n" + msg + " \n ========= \n");
    }
}
