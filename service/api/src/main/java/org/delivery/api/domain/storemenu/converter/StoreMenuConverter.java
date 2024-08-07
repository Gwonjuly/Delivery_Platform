package org.delivery.api.domain.storemenu.converter;

import org.delivery.common.annotation.Converter;
import org.delivery.common.error.ErrorCode;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class StoreMenuConverter {

   /* public StoreMenuEntity toEntity(StoreMenuRegisterRequest request, StoreEntity storeEntity){
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
    }*/

    public StoreMenuResponse toResponse(StoreMenuEntity entity){
        return Optional.ofNullable(entity)
                .map(it->{
                    return StoreMenuResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .storeId(entity.getStore().getId())
                            .amount(entity.getAmount())
                            .status(entity.getStatus())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .likeCount(entity.getLikeCount())
                            .sequence(entity.getSequence())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }
    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list){
       return list.stream()
               .map(
               it->toResponse(it))
               .collect(Collectors.toList());
    }
}
