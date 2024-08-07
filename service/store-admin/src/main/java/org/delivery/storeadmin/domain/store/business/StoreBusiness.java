package org.delivery.storeadmin.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.controller.model.StoreResponse;
import org.delivery.storeadmin.domain.store.converter.StoreConverter;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.delivery.common.annotation.Business;

import java.util.List;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    //가게 직원 등록 요청
    public StoreResponse register(StoreRegisterRequest storeRegisterRequest){
        var entity=storeConverter.toEntity(storeRegisterRequest);
        var newEntity=storeService.register(entity);
        var response=storeConverter.toResponse(newEntity);
        return response;
    }

    //csv -> store 초기화
    public void initStore(){
        List<StoreEntity> storeEntityList = storeService.loadStoreList();
        storeService.saveAll(storeEntityList);
    }
}
