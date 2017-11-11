package com.ml.spring.tuto.rgc.exceptions;

public final class InvalidReCaptchaException extends RuntimeException {

    public InvalidReCaptchaException() {
        super();
    }

    public InvalidReCaptchaException(String message) {
        super(message);
    }

    public InvalidReCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReCaptchaException(Throwable cause) {
        super(cause);
    }
}
