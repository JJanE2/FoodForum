package ourfood.example.foodforum.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ourfood.example.foodforum.exception.CustomAccessDeniedException;
import ourfood.example.foodforum.exception.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler(CustomAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleInvalidInputException(CustomAccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        return "error/403";
    }

    @ExceptionHandler({
            ResourceNotFoundException.class, NoHandlerFoundException.class, NoResourceFoundException.class,
            NumberFormatException.class, MethodArgumentTypeMismatchException.class, IllegalStateException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String resourceNotFound(Exception ex) {
        log.error(ex.getMessage(), ex);
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return "error/500";
    }
}
