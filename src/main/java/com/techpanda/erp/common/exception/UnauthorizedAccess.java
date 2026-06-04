package com.techpanda.erp.common.exception;


import org.springframework.http.HttpStatus;

public class UnauthorizedAccess
        extends BusinessException {

    public UnauthorizedAccess(
            String message
    ) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
