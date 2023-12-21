package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.JsonResponse;
import com.FMCSULconferencehandler.service.CustomDataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new JsonResponse(ex.getMessage(), null),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        LOGGER.error("RuntimeException: ", ex);
        return handleExceptionInternal(ex, new JsonResponse("server exception", null),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { EmptyResultDataAccessException.class })
    protected ResponseEntity<Object> handleResourceNotFoundException(EmptyResultDataAccessException ex, WebRequest request) {
        return handleExceptionInternal(ex, new JsonResponse(ex.getMessage(), null),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        LOGGER.error("", ex);
        return handleExceptionInternal(ex, new JsonResponse(ex.getMessage(), null),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { CustomDataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleCustomDataIntegrityViolationException(CustomDataIntegrityViolationException ex, WebRequest request) {
        LOGGER.error("", ex);
        return handleExceptionInternal(ex, new JsonResponse(ex.getMessage(), null),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
