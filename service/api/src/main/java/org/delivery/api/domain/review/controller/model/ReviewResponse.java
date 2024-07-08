package org.delivery.api.domain.review.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.review.enums.ReviewStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;
    private String userName;
    private double star;
    private String reviewText;
    private LocalDateTime reviewCreatedAt;
    private LocalDateTime reviewUpdatedAt;
    private String replyText;
    private LocalDateTime replyCreatedAt;
    private LocalDateTime replyUpdatedAt;
    private ReviewStatus status;
}
