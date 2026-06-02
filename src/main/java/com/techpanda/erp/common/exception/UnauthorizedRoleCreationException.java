package com.techpanda.erp.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedRoleCreationException
        extends BusinessException {

    public UnauthorizedRoleCreationException(
            String message
    ) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
