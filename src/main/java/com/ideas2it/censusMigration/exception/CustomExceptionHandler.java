package com.ideas2it.censusMigration.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ideas2it.censusMigration.DTO.ErrorMessageDTO;
import com.ideas2it.censusMigration.Logger.CustomLogger;

@RestControllerAdvice
public class CustomExceptionHandler {
    CustomLogger logger = new CustomLogger(ExceptionHandler.class);
    public ResponseEntity<ErrorMessageDTO> handelCustomException(CustomException exception) {
        logger.error(exception.getMessage());
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(exception.getMessage(),exception.getHttpStatus());
        return ResponseEntity.status(exception.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorMessageDTO> ioExceptionHandler(IOException ioException){
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ioException.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }


}
