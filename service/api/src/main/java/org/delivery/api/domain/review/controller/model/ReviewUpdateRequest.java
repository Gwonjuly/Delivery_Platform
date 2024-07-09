package org.delivery.api.domain.review.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateRequest {
    private Long reviewId;
    private double star;
    private String reviewText;
}
