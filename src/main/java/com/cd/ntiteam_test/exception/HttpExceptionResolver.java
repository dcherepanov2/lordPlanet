package com.cd.ntiteam_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Objects;

@RestControllerAdvice
public class HttpExceptionResolver {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HashMap<String, String> handleNoHandlerFound(NoHandlerFoundException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", e.getLocalizedMessage());
        response.put("class error", e.getClass().getName());
        return response;
    }

    @ExceptionHandler({
            LordException.class,
            PlanetException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, String> badRequest(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", ex.getMessage());
        response.put("http status", "400");
        return response;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, String> argumentTypeMismatchException(
            HttpServletRequest httpServletRequest,
            MethodArgumentTypeMismatchException ex) {
        String URI = httpServletRequest.getRequestURI();
        HashMap<String, String> response = new HashMap<>();
        String except = ex.getParameter().getParameterName();
        String type = Objects.requireNonNull(ex.getRequiredType()).getName();
        String value = type.substring(type.indexOf("g.") + 2);
        response.put("status", "fail");
        response.put("message", "Bad request. Parameter passing: "
                + except +
                " transmission by URI: " + URI + " have error. Parameter " + except +
                " expected type data " + value);
        response.put("http status", "400");
        return response;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static HashMap<String, String> bindException(BindException ex) {
        HashMap<String, String> response = new HashMap<>();
        if (ex.getMessage().contains("'lord' on field 'age'"))
            response.put("message", "Bad request. The age field must have the Integer data type.");
        if (ex.getMessage().contains("'lord' on field 'id'"))
            response.put("message", "Bad request. The age field must have the Integer data type.");
        response.put("status", "fail");
        response.put("http status", "400");
        return response;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public HashMap<String, String> notAcceptableError(
            HttpServletRequest httpServletRequest,
            HttpRequestMethodNotSupportedException ex
    ) {
        String URI = httpServletRequest.getRequestURI();
        String method = ex.getMethod();
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        if (method.contains("GET")) {
            response.put("message", "Request method " + method + " not supported for URI: " + URI
                    + ". Please select a POST method");
            response.put("http status", "416");
            return response;
        } else if (method.contains("POST")) {
            response.put("message", "Request method " + method + " not supported for URI: " + URI
                    + ". Please select a POST method");
            response.put("http status", "416");
            return response;
        }
        response.put("status", "fail");
        response.put("message", ex.getMessage());
        response.put("http status", "500");
        return response;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HashMap<String, String> handleError(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", ex.getMessage());
        response.put("class error", ex.getClass().getName());
        response.put("http status", "500");
        return response;
    }
}
