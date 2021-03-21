package com.myexaminer.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@Log4j2
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = {EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity handleNotFoundException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
