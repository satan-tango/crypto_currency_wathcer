package com.crypto.currency.cryptowatcher.exception;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {APIRequestException.class})
    public ResponseEntity<Object> handleAPIRequestException(APIRequestException e) {
        APIException apiException = new APIException(
                e.getMessage(),
                e.getCause(),
                org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_BAD_REQUEST),
                ZonedDateTime.now(ZoneId.of("Europe/Minsk"))
        );
        return new ResponseEntity<Object>(apiException, org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_BAD_REQUEST));
    }
}
