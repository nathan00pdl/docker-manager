package com.nathan.docker_manager.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;

/*
 * Maps the errors the Docker Engine reports to the HTTP status they stand for,
 * instead of letting every one of them reach the client as a 500.
 */
@RestControllerAdvice
public class DockerExceptionHandler {

    // unknown container or image
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(NotFoundException e) {
        return error(HttpStatus.NOT_FOUND, "No such container or image");
    }

    // starting a running container, or stopping a stopped one: the Engine answers 304
    @ExceptionHandler(NotModifiedException.class)
    public ResponseEntity<Map<String, Object>> notModified(NotModifiedException e) {
        return error(HttpStatus.CONFLICT, "The container is already in that state");
    }

    // for example, removing a container that is still running
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> conflict(ConflictException e) {
        return error(HttpStatus.CONFLICT, "The Docker Engine refused the operation: stop the container first");
    }

    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message));
    }
}
