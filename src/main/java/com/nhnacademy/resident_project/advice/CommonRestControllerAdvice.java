package com.nhnacademy.resident_project.advice;

import com.nhnacademy.resident_project.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.DateTimeException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class CommonRestControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }


    //400 Bad Request
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class,
            ValidationFailedException.class, DuplicateSerialNumberException.class,
            MissingParameterException.class, NoSuchRelationshipException.class,
            NoSuchElementException.class, InvalidTypeCodeException.class,
            DateTimeException.class})
    public ResponseEntity<ErrorMessage> mismatchParameter(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    //401 Unauthorized
    @ExceptionHandler(IllegalResidentAccessException.class)
    public ResponseEntity<ErrorMessage> unauthorizedUser(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    //404 Not Found
    @ExceptionHandler({ResidentNotFoundException.class, NoHandlerFoundException.class,
            })
    public ResponseEntity<ErrorMessage> eventNotFoundException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    //405 Method Not Allowed
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage> methodNotAllowed(Exception exception, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED.value(), exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    //500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalServerError(Exception exception) {
        exception.printStackTrace();
        log.error(exception.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
