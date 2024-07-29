package com.example.librarysystem.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ISBNConflictException extends RuntimeException{
	
	public ISBNConflictException(String message) {
        super(message);
    }

}
