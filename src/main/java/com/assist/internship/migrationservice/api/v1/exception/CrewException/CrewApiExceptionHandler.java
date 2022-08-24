package com.assist.internship.migrationservice.api.v1.exception.CrewException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CrewApiExceptionHandler {
    @ExceptionHandler(value = {CrewApiException.class})
    public ResponseEntity<Object> handleCrewApiException(CrewApiException e) {
        CrewException crewException = new CrewException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(crewException, HttpStatus.BAD_REQUEST);
    }
}
