package com.security.exception;

import com.security.dto.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFound exception, WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage(), webRequest.getDescription(false), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handlingException(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage(), webRequest.getDescription(false), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errorList = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String filedError = error.getDefaultMessage();

            errorList.put(fieldName, filedError);
        });
        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }


}
