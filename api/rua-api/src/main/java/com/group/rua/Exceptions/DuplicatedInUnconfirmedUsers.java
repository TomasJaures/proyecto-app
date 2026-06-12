package com.group.rua.Exceptions;

public class DuplicatedInUnconfirmedUsers extends RuntimeException {
    public DuplicatedInUnconfirmedUsers(String msg){
        super("\n ========= \n" + msg + " \n ========= \n");
    }
}
