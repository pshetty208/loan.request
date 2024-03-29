package com.ing.loan.request.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class InternalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectLoanAmountException.class)
    public Map<String,String> handleIncorrectLoanAmountException(IncorrectLoanAmountException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public Map<String,String> handleCustomerNotFoundException(CustomerNotFoundException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return map;
    }

    //More exception handlers

}

