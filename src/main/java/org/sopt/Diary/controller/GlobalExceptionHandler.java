package org.sopt.Diary.controller;

import org.sopt.Diary.exception.DiaryNotFoundException;
import org.sopt.Diary.exception.DuplicateTitleException;
import org.sopt.Diary.exception.LimitTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DiaryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDiaryNotFoundException(DiaryNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DuplicateTitleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDuplicateTitleException(DuplicateTitleException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(LimitTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRateLimitException(LimitTimeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }
}