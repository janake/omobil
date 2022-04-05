package hu.otp.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorMessage {

    private String errorCode;
    private String errorMessage;

}
