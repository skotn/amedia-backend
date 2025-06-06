package com.amediabackend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleInternalException(e: Exception): ResponseEntity<String> {
        //log it
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("something happened internally")
    }
}
