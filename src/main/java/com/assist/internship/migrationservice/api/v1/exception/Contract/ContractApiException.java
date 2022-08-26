package com.assist.internship.migrationservice.api.v1.exception.Contract;

public class ContractApiException extends RuntimeException{
    public ContractApiException(String message) {
        super(message);
    }

    public ContractApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContractApiException() {
    }
}
