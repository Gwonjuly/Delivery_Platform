package org.delivery.storeadmin.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.api.Api;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.userorder.business.UserOrderBusiness;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-order")
@Slf4j
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    @PostMapping("/accept")
    public Api<UserOrderDetailResponse> accept(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession,
            @RequestBody UserOrderRequest userOrderRequest
    ){
        log.info("userOrderRequest {}", userOrderRequest);
        var response = userOrderBusiness.accept(userOrderRequest);
        return Api.OK(response);
    }

    @PostMapping("/start-cooking")
    public Api<UserOrderDetailResponse> cooking(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession,
            @RequestBody UserOrderRequest userOrderRequest
    ){
        log.info("userOrderRequest {}", userOrderRequest);
        var response = userOrderBusiness.cooking(userOrderRequest);
        return Api.OK(response);
    }

    @PostMapping("/start-delivery")
    public Api<UserOrderDetailResponse> delivery(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession,
            @RequestBody UserOrderRequest userOrderRequest
    ){
        log.info("userOrderRequest {}", userOrderRequest);
        var response = userOrderBusiness.delivery(userOrderRequest);
        return Api.OK(response);
    }

    @PostMapping("/receive-delivery")
    public Api<UserOrderDetailResponse> receive(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession,
            @RequestBody UserOrderRequest userOrderRequest
    ){
        log.info("userOrderRequest {}", userOrderRequest);
        var response = userOrderBusiness.receive(userOrderRequest);
        return Api.OK(response);
    }
}
