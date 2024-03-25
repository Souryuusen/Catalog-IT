package com.soursoft.catalogit.exception;

public class WatchlistNotFoundException extends RuntimeException{

    public WatchlistNotFoundException(String message) {
        super(message);
    }
}
