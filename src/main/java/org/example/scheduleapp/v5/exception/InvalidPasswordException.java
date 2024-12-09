package org.example.scheduleapp.v5.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException() {
        super("Invalid password", HttpStatus.UNAUTHORIZED);
    }
}