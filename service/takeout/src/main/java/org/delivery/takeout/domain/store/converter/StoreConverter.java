package org.delivery.takeout.domain.store.converter;

import org.delivery.common.annotation.Converter;
import org.delivery.db.store.StoreEntity;
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
}
