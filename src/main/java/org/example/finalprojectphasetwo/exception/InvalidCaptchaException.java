package org.example.finalprojectphasetwo.exception;

public class InvalidCaptchaException extends RuntimeException{

    public InvalidCaptchaException(String message) {
        super(message);
    }
}
