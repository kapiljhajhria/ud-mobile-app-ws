package com.example.photoapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class AppExceptionsHandler {

    @ExceptionHandler(value = {UserServiceException.class}) // it can also handle more than one exception
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // you can use below code to handle multiple exceptions using one method. TO
    // handle
    // multiple exception you will have to change the function param to
    // Exception.class , a more generic type
    // @ExceptionHandler(value = {UserServiceException.class,
    // NullPointerException.class})
    // public ResponseEntity<Object> handleUserServiceException(Exception ex,
    // WebRequest request) {
    //
    // ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
    // return new ResponseEntity<>(errorMessage, new HttpHeaders(),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    @ExceptionHandler(value = {Exception.class, UserIdNotFoundException.class})
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            System.out.println(fieldName + " " + errorMessage);

            // if(fieldName.equals("defaultMessage") || fieldName.equals("field")){
            // errors.put(fieldName, errorMessage);
            // }
            log.info("fieldName : " + fieldName + " errorMessage : " + errorMessage);
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ErrorMessage(new Date(), errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}