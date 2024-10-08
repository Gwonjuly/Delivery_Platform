package org.delivery.takeout.domain.direction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.DirectionRepository;
import org.delivery.db.direction.DirectionEntity;
import org.delivery.takeout.domain.kakao.model.DocumentDto;
import org.delivery.takeout.domain.kakao.service.KakaoCategorySearchService;
import org.delivery.takeout.domain.store.service.StoreSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 20; //스토어 최대 검색 수
    private static final double RADIUS_KM = 10.0; //반경 10 km
    private static final String DIRECTION_MAP_DEFAULT_URL = "https://map.kakao.com/link/map/";
    private static final String ROAD_VIEW_DEFAULT_URL = "https://map.kakao.com/link/roadview/";

    private final StoreSearchService storeSearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final Base62Service base62Service;

    @Transactional
    public List<DirectionEntity> saveAll(List<DirectionEntity> directionEntityList){
        if(CollectionUtils.isEmpty(directionEntityList))
            throw new ApiException(ErrorCode.NULL_POINT,"direction entity null");
        return directionRepository.saveAll(directionEntityList);
    }

    public String searchDirectionById(String encodedId){

        Long decodedId = base62Service.decodeDirectionId(encodedId);
        DirectionEntity directionEntity= directionRepository.findById(decodedId).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"null"));

        String params = String.join(",", directionEntity.getTargetStoreName(),
                String.valueOf(directionEntity.getTargetLatitude()),
                String.valueOf(directionEntity.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_MAP_DEFAULT_URL + params)
                .toUriString();

        return result;
    }

    public String searchRoadViewById(String encodedId){

        Long decodedId = base62Service.decodeDirectionId(encodedId);
        DirectionEntity directionEntity= directionRepository.findById(decodedId).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"null"));

        String params = String.join(",",
                String.valueOf(directionEntity.getTargetLatitude()),
                String.valueOf(directionEntity.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(ROAD_VIEW_DEFAULT_URL + params)
                .toUriString();

        return result;
    }

    public List<DirectionEntity> buildDirectionList(DocumentDto documentDto){

        if(Objects.isNull(documentDto))
            throw new ApiException(ErrorCode.NULL_POINT,"Null");

        return storeSearchService.searchStoreDtoList().stream().map(it->
                DirectionEntity.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputLatitude(documentDto.getLatitude())
                        .inputLongitude(documentDto.getLongitude())
                        .targetStoreName(it.getName())
                        .targetAddress(it.getAddress())
                        .targetLatitude(it.getLatitude())
                        .targetLongitude(it.getLongitude())
                        .distance(
                                calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), it.getLatitude(), it.getLongitude())
                        )
                        .build())
                .filter(directionEntity -> directionEntity.getDistance() <= RADIUS_KM)//식당 REGISTERED 필터 추가
                .sorted(Comparator.comparing(DirectionEntity::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    public List<DirectionEntity> buildDirectionListByCategory(DocumentDto documentDto){
        if(Objects.isNull(documentDto))
            throw new ApiException(ErrorCode.NULL_POINT, "null by category");

        return kakaoCategorySearchService
                .searchCategory(documentDto.getLatitude(), documentDto.getLongitude(), RADIUS_KM)
                .getDocumentDtoList()
                .stream().map(it->
                        DirectionEntity.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetStoreName(it.getStoreName())
                                .targetAddress(it.getAddressName())
                                .targetLatitude(it.getLatitude())
                                .targetLongitude(it.getLongitude())
                                .distance(it.getDistance() * 0.001)
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }


    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
