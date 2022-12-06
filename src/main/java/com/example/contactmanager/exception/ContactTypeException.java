package com.example.contactmanager.exception;

public class ContactTypeException extends RuntimeException {

    public ContactTypeException(String message) {
        super(message);
    }

    public ContactTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
