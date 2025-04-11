package com.app.challenge.infrastructure.rest.advice;

public class ChallengeHandleException extends RuntimeException {
    public ChallengeHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
