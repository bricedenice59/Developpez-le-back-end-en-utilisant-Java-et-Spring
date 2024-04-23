package com.bricedenice59.chatop.models.responses;

import lombok.Data;

@Data
public class SimpleOutputMessageResponse {

    private String Message;
    public SimpleOutputMessageResponse(String message) {
        Message = message;
    }
}
