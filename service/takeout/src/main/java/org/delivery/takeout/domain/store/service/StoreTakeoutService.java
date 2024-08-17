package org.delivery.takeout.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.direction.DirectionEntity;
import org.delivery.takeout.domain.direction.controller.model.DirectionResponse;
import org.delivery.takeout.domain.direction.service.DirectionService;
import org.delivery.takeout.domain.kakao.model.DocumentDto;
import org.delivery.takeout.domain.kakao.model.KakaoApiResponse;
import org.delivery.takeout.domain.kakao.service.KakaoAddressSearchService;
import org.delivery.takeout.domain.store.converter.StoreConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreTakeoutService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final StoreConverter storeConverter;

    public List<DirectionResponse> storeTakeoutList(String address){

        KakaoApiResponse kakaoApiResponse = kakaoAddressSearchService.searchAddress(address);

        if(Objects.isNull(kakaoApiResponse) || CollectionUtils.isEmpty(kakaoApiResponse.getDocumentDtoList())) {
            log.error("입력한 주소 기반의 위치를 찾을 수 없습니다.{}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponse.getDocumentDtoList().get(0);

        List<DirectionEntity> directionEntityList = directionService.buildDirectionListByCategory(documentDto);

        return  directionService.saveAll(directionEntityList).stream()
                .map(storeConverter::toDirectionResponse)
                .collect(Collectors.toList());
    }
}
