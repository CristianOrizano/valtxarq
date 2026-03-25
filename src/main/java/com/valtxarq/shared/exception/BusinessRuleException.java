package com.valtxarq.shared.exception;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends CustomException {
    public BusinessRuleException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
