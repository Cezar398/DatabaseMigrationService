package com.assist.internship.migrationservice.api.v1.exception.Contract;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ContractApiExceptionHandler {
   @ExceptionHandler(value = {ContractApiException.class})
    public ResponseEntity<Object> handleCsvApiRequest(ContractApiException e) {
        ContractException contractException = new ContractException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(contractException, HttpStatus.BAD_REQUEST);
    }
}
