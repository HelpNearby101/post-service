package com.example.post.exception;

import com.example.post.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handlePostNotFoundException(
            PostNotFoundException exception) {

        return new ApiResponse(
                exception.getMessage(),
                com.example.post.enumeration.ResponseStatus.ERROR,
                null,
                404
        );
    }
}