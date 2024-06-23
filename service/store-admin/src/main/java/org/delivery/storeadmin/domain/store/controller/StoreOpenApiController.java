package org.delivery.storeadmin.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.business.StoreBusiness;
import org.delivery.common.api.Api;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.controller.model.StoreResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store")

public class StoreOpenApiController {

    private final StoreBusiness storeBusiness;

    @PostMapping("/register")//인자: 객체
    public Api<StoreResponse> register(
            @Valid
            @RequestBody Api<StoreRegisterRequest> request
            )
    {
        var response=storeBusiness.register(request.getBody());//Api<result+body> 형태로 getBody 없으면 에러남
        return Api.OK(response);
    }
}
