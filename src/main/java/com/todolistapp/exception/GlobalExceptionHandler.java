package com.todolistapp.exception;

import com.todolistapp.model.payload.response.ExceptionDto;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import jakarta.servlet.ServletException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler<E extends Throwable> {

    @ExceptionHandler(exception = {
            BadRequestException.class,
            DuplicateUserException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class
    })
    private ResponseEntity<ExceptionDto> badRequestExceptionHandler(E e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(exception = {
            HttpRequestMethodNotSupportedException.class,
    })
    private ResponseEntity<ExceptionDto> methodNotAllowedExceptionHandler(E e) {
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(exception = {
            NotFoundException.class,
            UsernameNotFoundException.class
    })
    private ResponseEntity<ExceptionDto> notFoundExceptionHandler(E e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(exception = {
            PasswordException.class
    })
    private ResponseEntity<ExceptionDto> unauthorizedExceptionHandler(E e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(exception = {
            AuthorizationDeniedException.class,
            AccessDeniedException.class
    })
    private ResponseEntity<ExceptionDto> forbiddenExceptionHandler(E e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(exception = {
            UnauthorizedException.class,
    })
    private ResponseEntity<ExceptionDto> UnauthorizedExceptionHandler(E e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<>();
        e.getAllErrors().forEach(oe -> errors.add(oe.getDefaultMessage()));
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(errors)
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }

    @ExceptionHandler(exception = {
            DecodingException.class,
            MalformedJwtException.class,
            ServletException.class,
            UnexpectedTypeException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    private ResponseEntity<ExceptionDto> serviceUnavailableExceptionHandler(E e) {
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        ExceptionDto exception = ExceptionDto.builder()
                .timeStamp(Instant.now().toString())
                .statusCode(httpStatus.value())
                .error(httpStatus.name())
                .messages(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(exception);
    }


}
