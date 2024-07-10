package org.delivery.api.domain.review.service;

import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.review.ReviewRepository;
import org.delivery.db.review.enums.ReviewStatus;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.user.UserRepository;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserOrderRepository userOrderRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<ReviewEntity> getUserOrderReview(Long userOrderId) {
        return reviewRepository.findByUserOrderId(userOrderId);
        //return null;
    }

    // 유저 리뷰 검색
    @Transactional(readOnly = true)
    public Page<ReviewEntity> getUserReview(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable);
    }
    //스토어 리뷰 검색
    @Transactional(readOnly = true)
    public Page<ReviewEntity> getStoreReview(Long storeId, Pageable pageable) {
        return reviewRepository.findByStoreId(storeId, pageable);
    }

    //리뷰 생성
    public ReviewEntity saveReview(ReviewEntity reviewEntity){
        reviewEntity.setStatus(ReviewStatus.REGISTERED);
        reviewEntity.setReviewCreatedAt(LocalDateTime.now());
        return reviewRepository.save(reviewEntity);
    }

    //리뷰 ID로 리뷰 검색
    @Transactional(readOnly = true)
    public ReviewEntity getReview(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
    }

    //리뷰 수정
    public void updateReview(ReviewEntity reviewEntity){
        try {
            ReviewEntity newReviewEntity = reviewRepository.getReferenceById(reviewEntity.getId());
            //TODO 주문 목록이 없거나, 본인이 주문한 것이 아닐 때 예외 처리 필요
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

    //유저 주문 리뷰 검색? 필요하면 넣자
}
