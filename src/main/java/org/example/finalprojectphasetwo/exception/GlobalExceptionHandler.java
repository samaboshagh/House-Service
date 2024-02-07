package org.example.finalprojectphasetwo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<String> duplicateExceptionHandler(DuplicateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SpecialistQualificationException.class)
    public ResponseEntity<String> specialistQualificationExceptionHandler(SpecialistQualificationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> invalidPasswordExceptionHandler(InvalidPasswordException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> invalidInputExceptionHandler(InvalidInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongTimeException.class)
    public ResponseEntity<String> wrongTimeExceptionHandler(WrongTimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCaptchaException.class)
    public ResponseEntity<String> invalidCaptchaExceptionHandler(InvalidCaptchaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "INVALID FORMAT")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleException(HttpMessageNotReadableException ex) {
        ex.getHttpInputMessage();
    }

}