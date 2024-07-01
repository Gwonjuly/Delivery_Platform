package org.delivery.storeadmin.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                    //todo 등록 일시 추가하기
                    return storeRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreEntity getStoreWithThrow(Long id){
        var entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return entity.orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"등록된 가게가 없습니다"));
    }
}
