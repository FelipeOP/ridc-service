package com.sgd.ridc.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import oracle.stellent.ridc.IdcClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> buildResponseEntity(
            Exception e,
            HttpServletRequest request,
            HttpStatus httpStatus) {
        ApiError apiError = new ApiError(
                httpStatus,
                e.toString(),
                request.getRequestURI());
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(IdcClientException.class)
    public ResponseEntity<ApiError> handleIdcClientException(IdcClientException e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e,
            HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
            HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e, HttpServletRequest request) {
        return buildResponseEntity(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
            HttpServletRequest request) {
        List<String> errors = getSeveralErrors(e);
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                e.toString(),
                request.getRequestURI());
        apiError.setErrors(errors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    private List<String> getSeveralErrors(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
    }

}
