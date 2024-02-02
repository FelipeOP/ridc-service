package com.sgd.ridc.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime localDateTime;
    private HttpStatus httpStatus;
    private int statusCode;
    private String message;
    private List<String> errors;
    private String path;

    public ApiError(HttpStatus error, String message, String path) {
        this.localDateTime = LocalDateTime.now();
        this.httpStatus = error;
        this.statusCode = error.value();
        this.message = message;
        this.path = path;
    }
}
