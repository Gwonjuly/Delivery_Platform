package org.delivery.takeout.domain.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.takeout.domain.kakao.model.KakaoApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {
//https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord
    private final RestTemplate restTemplate;

    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApikey;

    public KakaoApiResponse searchAddress(String address){

        if(ObjectUtils.isEmpty(address))
            throw new ApiException(ErrorCode.NULL_POINT);

        //kakao api의 파라미터 생성
        URI uri = kakaoUriBuilderService.buildUriByAddress(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApikey);// -H "Authorization: KakaoAK ${REST_API_KEY}"
        HttpEntity httpEntity= new HttpEntity<>(headers);

        //kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponse.class).getBody();
    }
}
