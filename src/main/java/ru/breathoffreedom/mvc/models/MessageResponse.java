package ru.breathoffreedom.mvc.models;

/**
 * this is helping class to return responses to client
 */
public class MessageResponse {
    private String response;
    public MessageResponse(String s) {
        this.response = s;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "response : '" + response + '\'' +
                '}';
    }
}
