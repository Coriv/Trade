package com.microservices.tradeservice.exceptionHandler;

import com.microservices.tradeservice.exception.NotEnoughFoundsException;
import com.microservices.tradeservice.exception.TradeNotFoundException;
import com.microservices.tradeservice.exception.TradeWithYourselfException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TradeNotFoundException.class)
    public ResponseEntity<Object> tradeNotFoundHandler(TradeNotFoundException e) {
        return new ResponseEntity<>("Trade with given ID does not exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughFoundsException.class)
    public ResponseEntity<Object> notEnoughFoundHandler(NotEnoughFoundsException e) {
        return new ResponseEntity<>("You don't have enough found to complete this transaction", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TradeWithYourselfException.class)
    public ResponseEntity<Object> tradeWithYourselfHandler(TradeWithYourselfException e) {
        return new ResponseEntity<>("You can not trade with yourself.", HttpStatus.BAD_REQUEST);
    }
}
