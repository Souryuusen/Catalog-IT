package com.soursoft.catalogit.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
