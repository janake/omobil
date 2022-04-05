package hu.otp.ticket.config;

public class ErrorResponse {
    private int status;
    private String message;
    private Object result;

    public ErrorResponse(int status, String message, Object result){
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ErrorResponse [statusCode=" + status + ", message=" + message +"]";
    }


}