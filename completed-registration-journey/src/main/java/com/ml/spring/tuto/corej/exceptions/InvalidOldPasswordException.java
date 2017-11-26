package com.ml.spring.tuto.corej.exceptions;

public class InvalidOldPasswordException extends RuntimeException {

    public InvalidOldPasswordException(String message) {
        super(message);
    }

    public InvalidOldPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
