package com.example.attendxbackendv2.servicelayer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotRelatedException extends RuntimeException {

    public NotRelatedException(String resourceName, String relationField, String resourceValue, String targetName) {
        super(String.format("%s not found with the given input data %s : '%s'. %s is not related to %s by field %s : '%s'",
                resourceName, relationField, resourceValue, resourceName, targetName, relationField, resourceValue));
    }
}

