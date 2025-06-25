package com.oz.demojar.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Collections;
import java.util.StringTokenizer;

@ControllerAdvice
public class ExceptionControllerAdvisor extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvisor.class);

    @ExceptionHandler(ClientException.class)
    public ResponseEntity handleClientException(ClientException ex) {
        ErrorResponseVO errorResponseVO = createErrorResponse(ex,String.valueOf(HttpStatus.BAD_REQUEST.value()));
        logger.error("Error Details :: "+ errorResponseVO.getDetail());
        logger.error("Exception stacktrace :: "+   ExceptionUtils.getStackTrace(ex));
        return new ResponseEntity<>(errorResponseVO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, SQLException.class, DataAccessException.class,
            DataIntegrityViolationException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity errorHandler(HttpServletRequest req, Exception ex) {

        Class<?> c = ex.getClass();
        String fullClassName = c.getName();
        String[] parts = fullClassName.split("\\.");
        String exName = (parts.length > 0) ? parts[parts.length - 1] : "";

        HttpStatus httpStatus;
        switch (exName) {
            case "InvalidDataAccessApiUsageException":
            case "MethodArgumentTypeMismatchException":
            case "HttpMessageNotReadableException":
            case "IllegalArgumentException":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case "DataIntegrityViolationException":
            case "NumberFormatException":
                httpStatus = HttpStatus.CONFLICT;
                break;
            case "SQLException":
            case "DataAccessException":
            case "JpaSystemException":
            case "ArrayIndexOutOfBoundsException":
            case "NestedServletException":
            case "NullPointerException":
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case "NoSuchElementException":
                httpStatus = HttpStatus.NO_CONTENT;
                break;
            default:
                httpStatus = HttpStatus.NOT_FOUND;

        }
        ErrorResponseVO errorResponseVO = createErrorResponse(ex,String.valueOf(httpStatus.value()));
        logger.error("Error Name ::" + errorResponseVO.getTitle());
        logger.error("Error Details :: "+ errorResponseVO.getDetail());
        logger.error("Exception stacktrace :: "+   ExceptionUtils.getStackTrace(ex));
        return new ResponseEntity<ErrorResponseVO>(errorResponseVO, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        ErrorResponseVO errorResponseVO = createErrorResponse(ex,String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return handleExceptionInternal(ex, errorResponseVO, headers, HttpStatus.BAD_REQUEST, request);

    }
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers, HttpStatus status,
                                                        WebRequest request) {
        ErrorResponseVO errorResponseVO = createErrorResponse(ex,String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return handleExceptionInternal(ex, errorResponseVO, headers, HttpStatus.BAD_REQUEST, request);

    }
    private ErrorResponseVO createErrorResponse(Exception exception,String statusCode){
        ErrorResponseVO errorResponseVO = new ErrorResponseVO();
        errorResponseVO.setStatus(statusCode);
        errorResponseVO.setTitle(exception.getClass().getName());
        String details = (exception.getMessage() == null) ? "No error message details." : exception.getMessage();
        errorResponseVO.setDetail(details);
        String stackTrace = "";
        for (StackTraceElement ste : exception.getStackTrace())
        {
            boolean isDemojarClass = Collections.list(new StringTokenizer(ste.getClassName(), "."))
                    .stream().anyMatch(str->"demojar".equals(str));
            if(isDemojarClass){
                stackTrace = stackTrace.concat(ste.toString());
            }
        }

        String truncStackTrace = stackTrace.substring(0,Math.min(stackTrace.length(),200));
        errorResponseVO.setTrace(truncStackTrace);
        return errorResponseVO;
    }

}