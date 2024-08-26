package org.delivery.storeadmin.domain.storemenu.converter;

import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request, StoreEntity storeEntity){
        return Optional.ofNullable(request)
                .map(it->{
                    return StoreMenuEntity.builder()
                            .store(storeEntity)
                            .name(request.getName())
                            .amount(request.getAmount())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity){
        return StoreMenuResponse.builder()
                .id(storeMenuEntity.getId())
                .storeId(storeMenuEntity.getStore().getId())
                .name(storeMenuEntity.getName())
                .status(storeMenuEntity.getStatus())
                .amount(storeMenuEntity.getAmount())
                .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                .likeCount(storeMenuEntity.getLikeCount())
                .sequence(storeMenuEntity.getSequence())
                .build();
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list){
        return list.stream()
                .map(it->{
                    return toResponse(it);
                })
                .collect(Collectors.toList());
    }
}

