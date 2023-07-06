package com.microservices.tradeservice.exceptionHandler;

import com.microservices.tradeservice.exception.NotEnoughFoundsException;
import com.microservices.tradeservice.exception.TradeNotFoundException;
import com.microservices.tradeservice.exception.TradeWithYourselfException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TradeNotFoundException.class)
    public ResponseEntity<Object> tradeNotFoundHandler(TradeNotFoundException e) {
        return new ResponseEntity<>("Trade with given ID does not exist", BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughFoundsException.class)
    public ResponseEntity<Object> notEnoughFoundHandler(NotEnoughFoundsException e) {
        return new ResponseEntity<>("You don't have enough found to complete this transaction", NOT_ACCEPTABLE);
    }

    @ExceptionHandler(TradeWithYourselfException.class)
    public ResponseEntity<Object> tradeWithYourselfHandler(TradeWithYourselfException e) {
        return new ResponseEntity<>("You can not trade with yourself.", BAD_REQUEST);
    }
}
