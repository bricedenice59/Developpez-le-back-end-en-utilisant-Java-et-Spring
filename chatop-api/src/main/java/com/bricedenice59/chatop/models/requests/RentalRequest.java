package com.bricedenice59.chatop.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@Data
public class RentalRequest {

    @NotBlank
    private String name;
    @NotNull
    private Double surface;
    @NotNull
    private Double price;
    private MultipartFile picture;
    @NotBlank
    private String description;
    @NotNull
    private Integer owner_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
