package org.delivery.storeadmin.domain.reply.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReplyService {

    private final ReviewRepository reviewRepository;

    //리뷰 ID로 리뷰 검색
    public ReviewEntity getReview(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public ReviewEntity saveReply(ReviewEntity reviewEntity, Long storeId) {
        // 본인 가게가 맞는지 확인
        // replyText가 있는지 확인
        if (!reviewEntity.getStore().getId().equals(storeId))
            throw new RuntimeException("reply: 권한이 없습니다.");
        if (reviewEntity.getReplyText() == null)
            throw new RuntimeException("reply: 내용이 없습니다.");
        return reviewRepository.save(reviewEntity);
    }

}