package org.delivery.storeadmin.domain.reply.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.reply.business.ReplyBusiness;
import org.delivery.storeadmin.domain.reply.controller.model.ReplyRegisterRequest;
import org.delivery.storeadmin.domain.reply.controller.model.ReviewWithReplyResponse;
import org.delivery.storeadmin.domain.store.business.StoreBusiness;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reply")
public class ReplyApiController {

    private final ReplyBusiness replyBusiness;

    //리뷰에 대한 reply 생성
    @PostMapping("/save")
    public ReviewWithReplyResponse saveReply(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession,
            @RequestBody ReplyRegisterRequest replyRegisterRequest
            ){
        var storeId = userSession.getStoreId();
        var response = replyBusiness.saveReply(replyRegisterRequest, storeId);
        return response;
    }

    //가게의 리뷰 보기, user-api의 viewStoreReview 동일할 듯
   // @GetMapping("/view")


}
