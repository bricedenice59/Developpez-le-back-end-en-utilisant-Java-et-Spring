package com.bricedenice59.chatop.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@Data
public class RentalRequest {

    @NotBlank
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;
    private Integer owner_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
