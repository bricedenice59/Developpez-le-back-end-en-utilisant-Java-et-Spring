package com.bricedenice59.chatop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//CREATE TABLE `MESSAGES` (
//        `id` integer PRIMARY KEY AUTO_INCREMENT,
//  `rental_id` integer,
//        `user_id` integer,
//        `message` varchar(2000),
//  `created_at` timestamp,
//        `updated_at` timestamp
//);

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rental rental;

    @Column(nullable = false, length = 2000)
    private String message;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
