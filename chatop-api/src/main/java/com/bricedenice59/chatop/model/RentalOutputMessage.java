package com.bricedenice59.chatop.model;

import lombok.Data;

@Data
public class RentalOutputMessage {

    private String Message;
    public RentalOutputMessage(String message) {
        Message = message;
    }
}
