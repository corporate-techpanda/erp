package com.techpanda.erp.common.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}