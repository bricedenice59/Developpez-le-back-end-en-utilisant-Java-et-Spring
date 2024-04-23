package com.bricedenice59.chatop.models.responses;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiErrorResponse {

    private Integer errorCode;
    private String errorMessage;
    private String stackTrace;
    private LocalDateTime timestamp;

    public ApiErrorResponse(Integer errorCode, String errorMessage, String stackTrace, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
        this.timestamp = timestamp;
    }
}
