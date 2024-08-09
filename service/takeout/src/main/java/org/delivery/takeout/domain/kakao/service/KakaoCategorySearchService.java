package org.delivery.takeout.domain.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.takeout.domain.kakao.model.KakaoApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;
    private final RestTemplate restTemplate;

    private static final String FOOD_CATEGORY = "FD6";
    private static final String CAFE_CATEGORY = "CE7";

    @Value("${KAKAO_REST_API_KEY}")
    private String kakaoRestApikey;

    public KakaoApiResponse searchCategory(double latitude, double longitude, double radius){
        URI uri = kakaoUriBuilderService.buildUrByCategory(latitude,longitude,radius,FOOD_CATEGORY);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApikey);// -H "Authorization: KakaoAK ${REST_API_KEY}"
        HttpEntity httpEntity= new HttpEntity<>(headers);

        //kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponse.class).getBody();
    }
}
