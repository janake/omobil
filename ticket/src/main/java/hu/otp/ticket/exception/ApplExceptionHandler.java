package hu.otp.ticket.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ApplExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RestException.class})
    ResponseEntity<ErrorMessage> handleRestExceptions(RestException exception, HttpServletRequest request) {
        ErrorMessage errorMessage;
        if (exception.getEvent() != null && !exception.getEvent().isEmpty()) {
            errorMessage = new ErrorMessage(exception.getErrorCode(), exception.getErrorMessage());
        } else {
            HttpHeaders responseHeaders = ((HttpServerErrorException.InternalServerError) exception.getException()).getResponseHeaders();
            errorMessage = new ErrorMessage(responseHeaders.get("ErrorCode").get(0), responseHeaders.get("ErrorMessage").get(0));
        }
        log.error(errorMessage.getErrorMessage(), errorMessage.getErrorCode());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}