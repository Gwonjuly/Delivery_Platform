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

    public URI buildUriByAddress (String address){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(KAKAO_SEARCH_ADDRESS_URL)
                .queryParam("query",address);

        URI uri = uriComponentsBuilder.build().encode().toUri();
        log.info("KakaoUriBuilderService-address: {}",address);
        log.info("KakaoUriBuilderService-uri:{}",uri);
        return uri;
    }
}
