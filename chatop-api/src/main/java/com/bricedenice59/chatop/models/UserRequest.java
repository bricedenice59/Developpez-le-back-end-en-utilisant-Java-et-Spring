package com.bricedenice59.chatop.models;

import lombok.Data;

@Data
public class UserRequest {

    private String email;
    private String name;
    private String password;
}
