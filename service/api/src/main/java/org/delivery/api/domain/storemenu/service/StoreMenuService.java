package org.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    //메뉴 가져오기
    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        var entity=storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id,StoreMenuStatus.REGISTERED);
        return entity.orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    //특ㅈ
    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId,StoreMenuStatus.REGISTERED);
    }
}
