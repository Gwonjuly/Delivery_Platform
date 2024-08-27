package org.delivery.storeadmin.domain.storeuser.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.storeuser.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.storeuser.service.StoreUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreService storeService;

    public StoreUserResponse register(StoreUserRegisterRequest request){
        var storeEntity = storeService.getRegisteredStoreByName(request.getStoreName());
        var entity=storeUserConverter.toEntity(request, storeEntity);
        var newEntity=storeUserService.register(entity);
        var response=storeUserConverter.toResponse(newEntity, storeEntity);
        return response;
    }
}
