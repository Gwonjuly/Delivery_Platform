package org.delivery.storeadmin.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.business.StoreBusiness;
import org.delivery.common.api.Api;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.controller.model.StoreResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store")

public class StoreOpenApiController {

    private final StoreBusiness storeBusiness;

    //가게 직원이 등록
    @PostMapping("/register")
    public Api<StoreResponse> register(
            @Valid
            @RequestBody Api<StoreRegisterRequest> request
            )
    {
        var response=storeBusiness.register(request.getBody());//Api<result+body> 형태로 getBody 없으면 에러남
        return Api.OK(response);
    }

    //csv 파일 업로드
    @GetMapping("/init")
    public String initStore() {
        storeBusiness.initStore();
        return "success!";
    }
}
