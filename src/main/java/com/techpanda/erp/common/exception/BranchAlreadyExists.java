package com.techpanda.erp.common.exception;

import org.springframework.http.HttpStatus;

public class BranchAlreadyExists extends  BusinessException{

    public BranchAlreadyExists(String message, HttpStatus status) {
        super(message, status);
    }
}
