package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.common.annotation.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreService storeService;

    //특정 스토어에 있는 메뉴들 get
    public List<StoreMenuResponse> search(Long storeId){
        var list=storeMenuService.getStoreMenuByStoreId(storeId);
        return list.stream()
                /*.map(it->{
                    return storeMenuConverter.toResponse(it);
                    = .map(it->storeMenuConverter.toResponse(it))
                })*/
                .map(storeMenuConverter::toResponse)//refer method
                .collect(Collectors.toList());
    }
}
