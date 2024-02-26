package com.example.attendxbackendv2.servicelayer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LecturerAlreadyExistException extends RuntimeException{
    public LecturerAlreadyExistException(String message) {
        super(message);
    }
}