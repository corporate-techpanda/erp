package com.techpanda.erp.common.exception;

import com.techpanda.erp.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex
    ) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                ex.getStatus().value(),
                                ex.getMessage()
                        )
                );
    }
}
