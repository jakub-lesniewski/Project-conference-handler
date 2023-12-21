package com.FMCSULconferencehandler.service;

public class CustomDataIntegrityViolationException extends RuntimeException {
    public CustomDataIntegrityViolationException(String message) {
        super(message);
    }

    public CustomDataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
