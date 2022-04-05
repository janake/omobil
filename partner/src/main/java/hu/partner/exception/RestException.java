package hu.partner.exception;

import lombok.Data;

@Data
public class RestException extends RuntimeException {

    private String event;
    private String errorMessage;
    private String errorCode;

    public RestException() {
        super();
    }

    public RestException(String event, String errorMessage, String errorCode) {
        super(errorMessage);
        this.event = event;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

}