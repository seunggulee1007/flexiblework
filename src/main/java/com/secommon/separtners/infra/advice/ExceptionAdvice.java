package com.secommon.separtners.infra.advice;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.advice.exceptions.ExpiredTokenException;
import com.secommon.separtners.infra.advice.exceptions.NoDepartmentException;
import com.secommon.separtners.utils.ApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.secommon.separtners.utils.ApiUtil.error;

@RestControllerAdvice
public class ExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiUtil.ApiResult<?>> newResponse( Throwable throwable, HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtil.ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(message, status), headers, status);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiUtil.ApiResult<?>> defaultException( Exception e) {
        log.error( "defaultException : {} ", e.getMessage() );
        e.printStackTrace();
        return newResponse( e, HttpStatus.UNPROCESSABLE_ENTITY );
    }

    @ExceptionHandler({
            ExpiredTokenException.class,
            TokenExpiredException.class
    })
    protected ResponseEntity<ApiUtil.ApiResult<?>> expiredTokenException( Exception e ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        log.error( "expiredTokenException : {} ", e.getMessage() );
        return new ResponseEntity<>(error(e.getMessage(), -401 ), headers, HttpStatus.UNAUTHORIZED );
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            BadRequestException.class,
            NoDepartmentException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        if (e instanceof MethodArgumentNotValidException ) {
            return newResponse(
                    ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

}
