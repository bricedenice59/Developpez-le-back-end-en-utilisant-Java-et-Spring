package com.bricedenice59.chatop.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RentalRequest {

    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;
    private Integer owner_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
