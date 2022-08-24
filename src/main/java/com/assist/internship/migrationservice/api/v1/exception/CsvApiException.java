package com.assist.internship.migrationservice.api.v1.exception;

public class CsvApiException extends RuntimeException{
    public CsvApiException(String message) {
        super(message);
    }

    public CsvApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvApiException() {
    }
}
