package com.ideas2it.censusMigration.exception;

import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CustomException {

    public FileNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
