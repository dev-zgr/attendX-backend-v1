package com.example.attendxbackendv2.servicelayer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StudentAlreadyExistException  extends RuntimeException {
    public StudentAlreadyExistException(String message) {
        super(message);
    }
}

