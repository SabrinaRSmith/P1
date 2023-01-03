package com.revature.boot.exceptions;

public class EntityNotFound extends RuntimeException{
    public EntityNotFound(String message){
        super(message);
    }
}
