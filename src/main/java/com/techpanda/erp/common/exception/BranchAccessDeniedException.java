package com.techpanda.erp.common.exception;

import org.springframework.http.HttpStatus;

public class BranchAccessDeniedException
        extends BusinessException {

    public BranchAccessDeniedException(
            String message
    ) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
