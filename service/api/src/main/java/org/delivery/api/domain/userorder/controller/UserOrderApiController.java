package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.common.annotation.UserSession;
import org.delivery.common.api.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/user-order")
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    // 사용자 주문하기
    @PostMapping("")
    public Api<UserOrderResponse> userOrder(
            @Valid
            @RequestBody Api<UserOrderRequest> userOrderRequest,

            @Parameter(hidden = true)
            @UserSession User user){
        var response=userOrderBusiness.userOrder(user, userOrderRequest.getBody());
        return Api.OK(response);
    }

    //현재 진행 중인 주문 조회
    @GetMapping("/current")
    public ModelAndView current(
            @Parameter(hidden = true)
            @UserSession User user
    ){
        var response=userOrderBusiness.current(user);
        ModelAndView model = new ModelAndView();
        model.addObject("orders", response);
        model.setViewName("userOrder/orderList");
        return model;
    }

    //과거 주문 내역 조회
    @GetMapping("/history")
    public ModelAndView history(
            @Parameter(hidden = true)
            @UserSession User user
    ){
        var response=userOrderBusiness.history(user);
        ModelAndView model = new ModelAndView();
        model.addObject("orders", response);
        model.setViewName("userOrder/orderList");
        return model;
    }

    //주문 1건에 대한 주문 내역 조회
    @GetMapping("/id/{orderId}")
    public ModelAndView read(
            @Parameter(hidden = true)
            @UserSession User user,
            @PathVariable Long orderId
    ){
        var response=userOrderBusiness.read(user, orderId);
        log.info("user:{}",response.getUserOrderResponse());
        log.info("store:{}",response.getStoreResponse());
        var temp = response.getStoreMenuResponseList().stream()
                .map(i-> i.getName())
                .collect(Collectors.toUnmodifiableList());
        log.info("menu:{}",temp);
        ModelAndView model = new ModelAndView();
        model.addObject("orders", response);
        model.setViewName("userOrder/oneOrder");
        return model;
    }

    @Transactional
    @GetMapping("/view-all")//
    public ModelAndView viewAll(
            @Parameter(hidden = true)
            @UserSession User user

    ){
        List<UserOrderDetailResponse> responses = userOrderBusiness.viewAll(user);
        ModelAndView model = new ModelAndView();
        model.addObject("orders", responses);
        model.setViewName("userOrder/orderList");
        return model;
    }

    @GetMapping("/alarm")
    public Api<Page<UserOrderResponse>> alarm(
            @Parameter(hidden = true)
            @UserSession User user,
            @Parameter(hidden = true)
            Pageable pageable){
        var responses = userOrderBusiness.alarmList(user,pageable);
        return Api.OK(responses);
    }
}
