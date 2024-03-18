package com.example.attendxbackendv2.servicelayer.exceptions;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException(String sessionId) {
        super(String.format("Session with id: %s has already expired", sessionId));
    }
}
