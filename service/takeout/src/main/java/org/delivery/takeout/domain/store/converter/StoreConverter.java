package org.delivery.takeout.domain.store.converter;

import lombok.extern.slf4j.Slf4j;
import org.delivery.common.annotation.Converter;
import org.delivery.db.direction.DirectionEntity;
import org.delivery.db.store.StoreEntity;
import org.delivery.takeout.domain.direction.controller.model.DirectionResponse;
import org.delivery.takeout.domain.store.model.StoreDto;
import org.springframework.web.util.UriComponentsBuilder;

@Converter
@Slf4j
public class StoreConverter {
    private static final String ROAD_VIEW_DEFAULT_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_MAP_DEFAULT_URL = "https://map.kakao.com/link/MAP/";

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

        String params = String.join(",", directionEntity.getTargetStoreName(),
                String.valueOf(directionEntity.getTargetLatitude()),
                String.valueOf(directionEntity.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_MAP_DEFAULT_URL + params)
                .toUriString();

        log.info("direction params: {}, url:{}",params,result);

        return DirectionResponse.builder()
                .addressName(directionEntity.getTargetAddress())
                .storeName(directionEntity.getTargetStoreName())
                .distance(String.format("%.2f km",directionEntity.getDistance()))
                .directionUrl(result)
                .roadViewUrl(ROAD_VIEW_DEFAULT_URL + directionEntity.getTargetLatitude() + "," + directionEntity.getTargetLongitude())
                .build();
    }
}
