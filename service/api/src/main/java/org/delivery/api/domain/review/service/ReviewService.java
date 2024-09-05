package org.delivery.api.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.delivery.db.review.ReviewEntity;
import org.delivery.db.review.ReviewRepository;
import org.delivery.db.review.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<ReviewEntity> getUserOrderReview(Long userOrderId) {
        return reviewRepository.findByUserOrderId(userOrderId);
    }

    public Page<ReviewEntity> getUserReview(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable);
    }

    public Page<ReviewEntity> getStoreReview(Long storeId, Pageable pageable) {
        return reviewRepository.findByStoreId(storeId, pageable);
    }

    public ReviewEntity saveReview(ReviewEntity reviewEntity){
        reviewEntity.setStatus(ReviewStatus.REGISTERED);
        reviewEntity.setReviewCreatedAt(LocalDateTime.now());
        return reviewRepository.save(reviewEntity);
    }

    public ReviewEntity getReview(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void updateReview(ReviewEntity reviewEntity){
        try {
            ReviewEntity newReviewEntity = reviewRepository.getReferenceById(reviewEntity.getId());
            if (reviewEntity.getReviewText() != null)
                newReviewEntity.setReviewText(reviewEntity.getReviewText());
            newReviewEntity.setStar(reviewEntity.getStar());
        }catch (EntityNotFoundException e){
            log.warn("리뷰 업데이트 실패, 업데이트 내용이 없음 - review:{}", reviewEntity);
        }
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
