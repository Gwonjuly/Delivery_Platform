package org.delivery.storeadmin.domain.store.converter;

import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.controller.model.StoreResponse;
import org.delivery.common.annotation.Converter;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {

    public StoreEntity toEntity(StoreRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return StoreEntity.builder()
                            .name(request.getName())
                            //.address(request.getAddress())
                            .category(request.getStoreCategory())
                            .minimumAmount(request.getMinimumAmount())
                            .minimumDeliveryAmount(request.getMinimumDeliveryAmount())
                            //.thumbnailUrl(request.getThumbnailUrl())
                            //.phoneNumber(request.getPhoneNumber())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreResponse toResponse(StoreEntity entity){
        return Optional.ofNullable(entity)
                .map(it->{
                    return StoreResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .status(entity.getStatus())
                            .category(entity.getCategory())
                            .address(entity.getAddress())
                            .minimumAmount(entity.getMinimumAmount())
                            .minimumDeliveryAmount(entity.getMinimumDeliveryAmount())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .phoneNumber(entity.getPhoneNumber())
                            .star(entity.getStar())
                            .longitude(entity.getLatitude())
                            .latitude(entity.getLatitude())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }
}
