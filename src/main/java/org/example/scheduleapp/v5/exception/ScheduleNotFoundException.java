package org.example.scheduleapp.v5.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends CustomException {
    public ScheduleNotFoundException(String id) {
        super("Schedule not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}