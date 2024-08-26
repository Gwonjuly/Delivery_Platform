package org.delivery.storeadmin.domain.reply.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Business;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.reply.controller.model.ReplyRegisterRequest;
import org.delivery.storeadmin.domain.reply.controller.model.ReviewWithReplyResponse;
import org.delivery.storeadmin.domain.reply.converter.ReplyConverter;
import org.delivery.storeadmin.domain.reply.service.ReplyService;
import org.delivery.storeadmin.domain.store.converter.StoreConverter;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Transactional
public class ReplyBusiness {

    private final ReplyService replyService;
    private final ReplyConverter replyConverter;
    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public ReviewWithReplyResponse saveReply(ReplyRegisterRequest replyRegisterRequest, Long storeId) {
        var reviewEntity = replyService.getReview(replyRegisterRequest.getReviewId());
        var newReviewEntity = Optional.ofNullable(reviewEntity)
                .map(it ->{
                    it.setReplyText(replyRegisterRequest.getReplyText());
                    it.setReplyCreatedAt(LocalDateTime.now());
                    return replyService.saveReply(it, storeId);
                }).orElseThrow(EntityExistsException::new);
        var response = replyConverter.toResponse(newReviewEntity);
        return response;
    }

    public void deleteReview(UserSession userSession, Long reviewId) {
        replyService.deleteReview(reviewId);
    }

    public List<ReviewWithReplyResponse> viewStoreReview(Long storeId, Pageable pageable) {
        var storeReviewEntityPage = replyService.getStoreReview(storeId,pageable);
        var storeReplyEntityList= storeReviewEntityPage.stream()
                .map(replyConverter::toResponse)
                .collect(Collectors.toUnmodifiableList());
        return storeReplyEntityList;
    }
}
