package org.delivery.storeadmin.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.stereotype.Service;

@Service//편의상 @Business를 만들긴 했지만 걍 service로 해도 상관없음
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;// TODO StoreService로 변경하가

    public StoreUserResponse register(StoreUserRegisterRequest request){
        var storeEntity=storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);
        var entity=storeUserConverter.toEntity(request, storeEntity.get());
        var newEntity=storeUserService.register(entity);
        var response=storeUserConverter.toResponse(newEntity, storeEntity.get());
        return response;
    }
}
