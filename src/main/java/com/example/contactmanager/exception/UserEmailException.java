package com.example.contactmanager.exception;

public class UserEmailException extends RuntimeException {

    public UserEmailException(String message) {
        super(message);
    }

    public UserEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
