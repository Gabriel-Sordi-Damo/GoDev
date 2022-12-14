package com.example.springbootgodevbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidValueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidValueException(String message) {
        super(message);
    }
}
