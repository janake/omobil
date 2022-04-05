package hu.partner.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RestException.class, IllegalArgumentException.class})
    ResponseEntity<Map<String, String>> handleRestExceptions(RestException exception, WebRequest webrequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("ErrorCode", exception.getErrorCode());
        httpHeaders.add("ErrorMessage", exception.getErrorMessage());
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("ErrorCode", exception.getErrorCode());
        errorMessage.put("ErrorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(errorMessage, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
