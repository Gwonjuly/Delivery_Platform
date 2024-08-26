package org.delivery.storeadmin.domain.reply.converter;

import org.delivery.common.annotation.Converter;
import org.delivery.db.review.ReviewEntity;
import org.delivery.storeadmin.domain.reply.controller.model.ReviewWithReplyResponse;

@Converter
public class ReplyConverter {

    public ReviewWithReplyResponse toResponse(ReviewEntity reviewEntity){

        return ReviewWithReplyResponse.builder()
                .reviewId(reviewEntity.getId())
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
}
