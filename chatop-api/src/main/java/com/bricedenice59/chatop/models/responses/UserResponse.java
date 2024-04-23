package com.bricedenice59.chatop.models.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

    private Integer id;
    private String name;
    private String email;
    private String created_at;
    private String updated_at;
}
