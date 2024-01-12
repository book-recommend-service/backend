package com.pikabook.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity exception(
            Exception e
    ){
        log.error("RestApiExceptionHandler : ",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.ordinal()).body("알수없는 에러 발생");
    }
}
