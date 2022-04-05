package hu.otp.core.exception;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RestException.class, IllegalArgumentException.class})
    ResponseEntity<Object> handleRestExceptions(RestException exception, WebRequest webrequest) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getErrorCode(), exception.getErrorMessage());
        log.error(exception.getErrorMessage(), exception.getErrorCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("ErrorCode", exception.getErrorCode());
        httpHeaders.add("ErrorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(errorMessage, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
