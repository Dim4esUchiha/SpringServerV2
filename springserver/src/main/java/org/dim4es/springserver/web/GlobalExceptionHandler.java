package org.dim4es.springserver.web;

import org.dim4es.springserver.services.exception.EntityNotFoundException;
import org.dim4es.springserver.services.exception.UnprocessableEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnprocessableEntityException.class})
    public ResponseEntity<Object> handleUnprocessableEntityException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
