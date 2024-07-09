package org.delivery.api.domain.review.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private String storeName;
    private Long userOrderId;
    private double star;
    private String reviewText;
}
