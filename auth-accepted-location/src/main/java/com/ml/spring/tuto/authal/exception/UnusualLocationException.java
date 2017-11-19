package com.ml.spring.tuto.authal.exception;

import org.springframework.security.core.AuthenticationException;

public class UnusualLocationException extends AuthenticationException {

    public UnusualLocationException(String message) {
        super(message);
    }

    public UnusualLocationException(String msg, Throwable t) {
        super(msg, t);
    }
}
