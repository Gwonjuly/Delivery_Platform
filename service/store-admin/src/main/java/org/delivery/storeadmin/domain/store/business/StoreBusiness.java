package org.delivery.storeadmin.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.controller.model.StoreResponse;
import org.delivery.storeadmin.domain.store.converter.StoreConverter;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.delivery.common.annotation.Business;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    //등록 요청
    public StoreResponse register(StoreRegisterRequest storeRegisterRequest){
        var entity=storeConverter.toEntity(storeRegisterRequest);
        var newEntity=storeService.register(entity);
        var response=storeConverter.toResponse(newEntity);
        return response;
    }
}
