package com.soursoft.catalogit.exception;

public class UserByIdNotFoundException extends RuntimeException{

    public UserByIdNotFoundException(String message) {
        super(message);
    }
}
