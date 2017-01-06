package ru.breathoffreedom.mvc.models;

import org.springframework.http.HttpStatus;

/**
 * this is helping class to return responses to client
 */
public class MessageResponse {
    private String message;
    private HttpStatus status;

    public MessageResponse(HttpStatus status, String s) {
        this.message = s;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
