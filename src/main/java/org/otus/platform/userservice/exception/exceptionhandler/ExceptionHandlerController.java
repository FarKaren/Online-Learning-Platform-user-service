package org.otus.platform.userservice.exception.exceptionhandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.platform.userservice.dto.error.ArgumentNotValidDto;
import org.otus.platform.userservice.dto.error.ErrorDto;
import org.otus.platform.userservice.dto.error.MissingParameterDto;
import org.otus.platform.userservice.exception.exceptions.EmailAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class ExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> serverExceptionHandler(Exception e) {
        log.error("Internal server error. " + e.getMessage());
        return createResponseEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                new ErrorDto(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        e.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.error("Entity not found exception. " + e.getMessage());
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                new ErrorDto(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                        e.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<MissingParameterDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("Request parameter is missing. " + e.getParameterName());
        MissingParameterDto body = new MissingParameterDto(e.getParameterName(), e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> httpMessageNotReadableExceptionHandler(Exception e) {
        log.error("Http Message Not Readable Exception. " + e.getMessage());
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                new ErrorDto(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                        "JSON parse error"));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArgumentNotValidDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("Method argument not valid. " + e.getMessage());
        List<String> errors = new ArrayList<String>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ArgumentNotValidDto body = new ArgumentNotValidDto(HttpStatus.BAD_REQUEST, e.getMessage(), errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDto> emailAlreadyExistException(EmailAlreadyExistException e) {
        log.error("User with such email already exist. " + e.getMessage());
        return createResponseEntity(HttpStatus.BAD_REQUEST,
                new ErrorDto(Integer.toString(HttpStatus.BAD_REQUEST.value()), e.getMessage()));
    }
    
    private ResponseEntity<ErrorDto> createResponseEntity(HttpStatus status, ErrorDto errorDto) {
        return ResponseEntity.status(status)
                .header("Content-Type", "application/json")
                .body(errorDto);
    }

    private ResponseEntity.BodyBuilder getDefaultResponseEntityBuilder(int status) {
        return ResponseEntity.status(HttpStatus.valueOf(status)).contentType(MediaType.APPLICATION_JSON);
    }

    private String getDecodedResponseBody(ByteBuffer byteBuffer) {
        return StandardCharsets.UTF_8.decode(byteBuffer).toString();
    }
}
