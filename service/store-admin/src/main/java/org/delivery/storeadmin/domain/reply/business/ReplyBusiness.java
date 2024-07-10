package org.delivery.storeadmin.domain.reply.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Business;
import org.delivery.common.annotation.UserSession;
import org.delivery.storeadmin.domain.reply.controller.model.ReplyRegisterRequest;
import org.delivery.storeadmin.domain.reply.controller.model.ReviewWithReplyResponse;
import org.delivery.storeadmin.domain.reply.converter.ReplyConverter;
import org.delivery.storeadmin.domain.reply.service.ReplyService;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.Optional;

@Business
@RequiredArgsConstructor
public class ReplyBusiness {

    private final ReplyService replyService;
    private final ReplyConverter replyConverter;

    public ReviewWithReplyResponse saveReply(ReplyRegisterRequest replyRegisterRequest, Long storeId) {
        var reviewEntity = replyService.getReview(replyRegisterRequest.getReviewId());
       // var newReviewEntity = replyService.saveReply(reviewEntity, storeId);
        var newReviewEntity = Optional.ofNullable(reviewEntity)
                .map(it ->{
                    it.setReplyText(replyRegisterRequest.getReplyText());
                    it.setReplyCreatedAt(LocalDateTime.now());
                    return replyService.saveReply(it, storeId);
                }).orElseThrow(EntityExistsException::new);
        var response = replyConverter.toResponse(newReviewEntity);
        return response;
    }
}
