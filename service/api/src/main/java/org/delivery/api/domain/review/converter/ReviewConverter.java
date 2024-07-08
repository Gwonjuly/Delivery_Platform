package org.delivery.api.domain.review.converter;

import org.delivery.api.domain.review.controller.model.ReviewRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.common.annotation.Converter;
import org.delivery.db.review.ReviewEntity;

@Converter
public class ReviewConverter {

    public ReviewResponse toResponse(ReviewEntity reviewEntity){

        var nickName = reviewEntity.getUser().getName();
        if (nickName == null || nickName.isBlank()) {
            nickName = reviewEntity.getUser().getId().toString();
        }

        return ReviewResponse.builder()
                .id(reviewEntity.getId())
                .userName(reviewEntity.getUser().getName())
                .star(reviewEntity.getStar())
                .reviewText(reviewEntity.getReviewText())
                .reviewCreatedAt(reviewEntity.getReviewCreatedAt())
                .reviewUpdatedAt(reviewEntity.getReviewUpdatedAt())
                .replyText(reviewEntity.getReplyText())
                .replyCreatedAt(reviewEntity.getReplyCreatedAt())
                .replyUpdatedAt(reviewEntity.getReplyUpdatedAt())
                .status(reviewEntity.getStatus())
                .build();
    }

 /*   public ReviewEntity toEntity(ReviewRequest reviewRequest) {
    }*/
}
