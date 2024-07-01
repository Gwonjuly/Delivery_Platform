package org.delivery.storeadmin.domain.storemenu.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.common.api.Api;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/store-menu")
@RequiredArgsConstructor
public class StoreMenuApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    @PostMapping("/register")
    public Api<StoreMenuResponse> register(
            @Valid
            @RequestBody Api<StoreMenuRegisterRequest> request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
            ){
        var response=storeMenuBusiness.register(request.getBody());
        return Api.OK(response);
    }
}
