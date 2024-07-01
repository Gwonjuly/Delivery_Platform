package org.delivery.storeadmin.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.common.annotation.Business;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreService storeService;

    public StoreMenuResponse register(StoreMenuRegisterRequest request){

        var storeEntity=storeService.getStoreWithThrow(request.getStoreId());
        var entity=storeMenuConverter.toEntity(request,storeEntity);
        var newEntity=storeMenuService.register(entity);
        var response=storeMenuConverter.toResponse(newEntity);
        return response;
    }

}
