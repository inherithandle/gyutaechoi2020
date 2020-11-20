package com.gyutaechoi.kakaopay.controller;

import com.gyutaechoi.kakaopay.dto.ApiErrorResponse;
import com.gyutaechoi.kakaopay.exception.BadRequestException;
import com.gyutaechoi.kakaopay.exception.ForbiddenException;
import com.gyutaechoi.kakaopay.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public @ResponseBody ApiErrorResponse handle403(Exception e) {
        ApiErrorResponse res = new ApiErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return res;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody ApiErrorResponse handle404(Exception e) {
        ApiErrorResponse res = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return res;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody ApiErrorResponse handle401(Exception e) {
        ApiErrorResponse res = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return res;
    }

}
