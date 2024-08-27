package org.delivery.storeadmin.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.util.CsvUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    //스토어 등록하기
    public StoreEntity register(StoreEntity storeEntity, StoreRegisterRequest request){
        return Optional.ofNullable(storeEntity)
                .map(it->{
                    it.setStar(3);
                    it.setStatus(StoreStatus.REGISTERED);
                    it.setMinimumAmount(request.getMinimumAmount());
                    it.setMinimumDeliveryAmount(request.getMinimumDeliveryAmount());
                    return it;
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreEntity getStoreWithThrow(Long id){
        var entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return entity.orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"등록된 가게가 없습니다"));
    }

    public StoreEntity getStoreByName(String name){
        var entity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(name,StoreStatus.UNREGISTERED);
        return entity.orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"해당 이름의 가게가 없거나, 이미 등록된 가게 입니다."));
    }

    public StoreEntity getRegisteredStoreByName(String name){
        var entity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(name,StoreStatus.REGISTERED);
        return entity.orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"해당 이름의 가게가 없거나, 등록되지 않은 가게 입니다."));
    }

    public List<StoreEntity> loadStoreList(){
        return CsvUtils.csvToStoreResponse()
                .stream().map(it->
                    StoreEntity.builder()
                            .star(0)
                            .minimumAmount(BigDecimal.ZERO)
                            .minimumDeliveryAmount(BigDecimal.ZERO)
                            .phoneNumber(it.getPhoneNumber())
                            .thumbnailUrl(it.getThumbnailUrl())
                            .name(it.getName())
                            .address(it.getAddress())
                            .category(it.getCategory())
                            .latitude(it.getLatitude())
                            .longitude(it.getLongitude())
                            .status(StoreStatus.UNREGISTERED)
                            .build()
                ).collect(Collectors.toList());
    }

    public List<StoreEntity> saveAll(List<StoreEntity> storeEntityList){
        if(CollectionUtils.isEmpty(storeEntityList))
            return Collections.emptyList();
        return storeRepository.saveAll(storeEntityList);
    }
}
