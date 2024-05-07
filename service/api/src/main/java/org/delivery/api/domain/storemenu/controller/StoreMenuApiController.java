package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store-menu")
@RequiredArgsConstructor
public class StoreMenuApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    //특정 가게 클릭 시, 클릭한 storeId로 그 가게의 정보(메뉴) 내리기
    @GetMapping("/search")//query parameter
    public Api<List<StoreMenuResponse> > search(
            @RequestParam Long storeId
    ){
        var response=storeMenuBusiness.search(storeId);
        return Api.OK(response);
    }
}