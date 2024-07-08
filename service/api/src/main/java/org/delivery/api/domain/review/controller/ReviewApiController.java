package org.delivery.api.domain.review.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.domain.review.business.ReviewBusiness;
import org.delivery.api.domain.review.controller.model.ReviewRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.common.annotation.UserSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
@Slf4j
public class ReviewApiController {

    private final ReviewBusiness reviewBusiness;

    @GetMapping("/view")
    public ModelAndView view(
            @Parameter(hidden = true)
            @UserSession User user,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "ReviewCreatedAt", direction = Sort.Direction.DESC)Pageable pageable
            ){
        ModelAndView model = new ModelAndView();
        var responses = reviewBusiness.view(user, pageable);
        //log.info("view response {}", responses);
        model.addObject("reviews", responses);
        model.setViewName("review/view");
        return model;
        //return (ReviewDetailResponse) responses;
    }

    @GetMapping("/form/id/{userOrderId}")
    public ModelAndView formReview(
            @Parameter(hidden = true)
            @UserSession User user,
            @PathVariable Long userOrderId
    ){
        ModelAndView model = new ModelAndView();
        //유저 오더 ID로 리뷰 작성 페이지 요청  받기
        //유저 오더 ID로 해당 리뷰의 등록 여부 확인
        // 등록 시, 리뷰 엔티티의 모든 내용 반환
        // 미 등록 시, 가게 이름과 주문 내용 반환
        var response = reviewBusiness.formReview(user, userOrderId);
        model.addObject("review", response);
        model.setViewName("review/form");
        return model;
    }

    /*@PostMapping("/save")
    public ReviewResponse saveReview(
            @Parameter(hidden = true)
            @UserSession User user,
            @RequestBody ReviewRequest reviewRequest
            ){
        ModelAndView model = new ModelAndView();
        var response = reviewBusiness.saveReview(user,reviewRequest);
        return null;
    }*/

}
