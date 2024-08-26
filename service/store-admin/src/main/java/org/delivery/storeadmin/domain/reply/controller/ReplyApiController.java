package org.delivery.storeadmin.domain.reply.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.reply.business.ReplyBusiness;
import org.delivery.storeadmin.domain.reply.controller.model.ReplyRegisterRequest;
import org.delivery.storeadmin.domain.reply.controller.model.ReviewWithReplyResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    //가게의 리뷰 보기
    @GetMapping("/view")
    public List<ReviewWithReplyResponse> viewStoreReview(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "ReviewCreatedAt", direction = Sort.Direction.DESC) Pageable pageable){
        ModelAndView model = new ModelAndView();
        var response = replyBusiness.viewStoreReview(userSession.getStoreId(),pageable);
        model.addObject("reviews",response);
        model.setViewName("reply/view");//임시
        return response;
    }


    // review 삭제(가맹점 직원이 유저 리뷰 삭제)
    @PostMapping ("reviewId/delete")
    public void deleteReply(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession,
            @PathVariable Long reviewId
    ){
        replyBusiness.deleteReview(userSession, reviewId);
    }

}
