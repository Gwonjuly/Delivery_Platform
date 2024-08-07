package org.delivery.storeadmin.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
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
    public StoreEntity register(StoreEntity storeEntity){
        return Optional.ofNullable(storeEntity)//ofNullable: null 허용
                .map(it->{
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    return storeRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreEntity getStoreWithThrow(Long id){
        var entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return entity.orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"등록된 가게가 없습니다"));
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
