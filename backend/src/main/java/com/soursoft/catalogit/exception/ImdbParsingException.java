package com.soursoft.catalogit.exception;

public class ImdbParsingException extends RuntimeException{

    public ImdbParsingException(String message) {
        super(message);
    }

    public ImdbParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
