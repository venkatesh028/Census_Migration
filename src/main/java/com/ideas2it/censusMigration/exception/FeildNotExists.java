package com.ideas2it.censusMigration.exception;

import org.springframework.http.HttpStatus;

public class FeildNotExists extends CustomException{

    public FeildNotExists (String message){
        super(message, HttpStatus.NO_CONTENT);
    }
}
