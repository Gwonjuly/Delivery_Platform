package org.delivery.takeout.domain.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoUriBuilderService {

    private static final String KAKAO_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    private static final String KAKAO_SEARCH_CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddress (String address){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(KAKAO_SEARCH_ADDRESS_URL)
                .queryParam("query",address);

        URI uri = uriComponentsBuilder.build().encode().toUri();
        log.info("KakaoUriBuilderService-address: {}",address);
        log.info("KakaoUriBuilderService-uri:{}",uri);
        return uri;
    }

    public URI buildUrByCategory(double latitude, double longitude, double radius, String category){
        double meterRadius = radius * 100;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(KAKAO_SEARCH_CATEGORY_URL)
                .queryParam("category_group_code",category)
                .queryParam("x",longitude)
                .queryParam("y",latitude)
                .queryParam("radius",meterRadius)
                .queryParam("sort","distance");

        URI uri = uriComponentsBuilder.build().encode().toUri();

        log.info("KakaoUriBuilderService-uri:{}",uri);
        return uri;
    }
}
