package com.revature.boot.exceptions;

public class AuthenticationFailed extends RuntimeException{
    public AuthenticationFailed(String message){
        super(message);
    }
}
