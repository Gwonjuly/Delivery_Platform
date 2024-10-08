package org.delivery.api.domain.review.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.domain.review.business.ReviewBusiness;
import org.delivery.api.domain.review.controller.model.*;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.common.annotation.UserSession;
import org.springframework.data.domain.Page;
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
    private final UserService userService;

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
        var response = reviewBusiness.formReview(user, userOrderId);
        model.addObject("review", response);
        if(response.getReviewResponse() == null)
            model.addObject("formStatus",FormStatus.CREATE);
        else
            model.addObject("formStatus", FormStatus.UPDATE);
        model.setViewName("review/form");
        return model;
    }

    @PostMapping("/save")
    public ReviewResponse saveReview(
            @Parameter(hidden = true)
            @UserSession User user,
            @RequestBody ReviewRegisterRequest reviewRegisterRequest
            ){
        ModelAndView model = new ModelAndView();
        var response = reviewBusiness.saveReview(user, reviewRegisterRequest);
        model.setViewName("review/view");
        //return model;
        return response;
    }

    @PostMapping("update/id/{reviewId}")
    public ReviewResponse updateReview(
            @Parameter(hidden = true)
            @UserSession User user,
            @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ){
        ModelAndView model = new ModelAndView();
        var response = reviewBusiness.updateReview(user, reviewUpdateRequest);
        return response;
    }

    @DeleteMapping("/delete/id/{reviewId}")
    public void deleteReview(
            @Parameter(hidden = true)
            @UserSession User user,
            @PathVariable Long reviewId
    ){
        reviewBusiness.deleteReview(user, reviewId);
    }

    @GetMapping("/view/store-review/id/{storeId}")
    public Page<ReviewDetailResponse> viewStoreReview(
            @Parameter(hidden = true)
            @UserSession User user,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "ReviewCreatedAt", direction = Sort.Direction.DESC)Pageable pageable,
            @PathVariable Long storeId
    ){
        ModelAndView model = new ModelAndView();
        var response = reviewBusiness.viewStoreReview(storeId, pageable);
        model.addObject("reviews", response);
        model.setViewName("review/storeReview/view");
        return response;
    }
}
