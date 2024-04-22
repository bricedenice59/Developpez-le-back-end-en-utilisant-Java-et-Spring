package com.bricedenice59.chatop.models;

import lombok.Data;

@Data
public class ApiActionSuccessOutputMessage {

    private String Message;
    public ApiActionSuccessOutputMessage(String message) {
        Message = message;
    }
}
