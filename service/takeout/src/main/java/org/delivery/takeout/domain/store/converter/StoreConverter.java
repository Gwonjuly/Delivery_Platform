package org.delivery.takeout.domain.store.converter;

import org.delivery.common.annotation.Converter;
import org.delivery.db.direction.DirectionEntity;
import org.delivery.db.store.StoreEntity;
import org.delivery.takeout.domain.direction.controller.model.DirectionResponse;
import org.delivery.takeout.domain.store.model.StoreDto;

@Converter
public class StoreConverter {

    public StoreDto toStoreDto(StoreEntity storeEntity){
        return StoreDto.builder()
                .id(storeEntity.getId())
                .name(storeEntity.getName())
                .address(storeEntity.getAddress())
                .latitude(storeEntity.getLatitude())
                .longitude(storeEntity.getLongitude())
                .build();
    }

    public DirectionResponse toDirectionResponse(DirectionEntity directionEntity){
        return DirectionResponse.builder()
                .addressName(directionEntity.getTargetAddress())
                .storeName(directionEntity.getTargetStoreName())
                .distance(String.format("%.2f km",directionEntity.getDistance()))
                .directionUrl("direction Url")
                .roadViewUrl("road view Url")
                .build();
    }
}
