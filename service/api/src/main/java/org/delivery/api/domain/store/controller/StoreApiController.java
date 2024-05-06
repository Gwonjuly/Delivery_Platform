package org.delivery.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/search")//인자: 변수 or 객체
    public Api<List<StoreResponse>> search(
        @RequestParam(required = false)//필수 값 아님
        StoreCategory storeCategory
    ){
        var response=storeBusiness.searchCategory(storeCategory);
        return Api.OK(response);
    }






}
