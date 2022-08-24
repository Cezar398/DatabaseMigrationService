package com.assist.internship.migrationservice.api.v1.exception.CSV;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CsvApiExceptionHandler {
   @ExceptionHandler(value = {CsvApiException.class})
    public ResponseEntity<Object> handleCsvApiRequest(CsvApiException e) {
        CsvException csvException = new CsvException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(csvException, HttpStatus.BAD_REQUEST);
    }
}
