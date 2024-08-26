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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
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

    @Value("${KAKAO_REST_API_KEY}")
    private String kakaoRestApikey;

    @Retryable(
            value = {Exception.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public KakaoApiResponse searchAddress(String address){

        if(ObjectUtils.isEmpty(address))
            //return null;
            throw new ApiException(ErrorCode.NULL_POINT);

        //kakao api의 파라미터 생성
        URI uri = kakaoUriBuilderService.buildUriByAddress(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApikey);// -H "Authorization: KakaoAK ${REST_API_KEY}"
        log.info("헤더:{}",headers);
        HttpEntity httpEntity= new HttpEntity<>(headers);

        //kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponse.class).getBody();
    }

    //searchAddress 실패 시, recover 메서드 실행
    @Recover
    public KakaoApiResponse recover(Exception e, String address){
        log.error("모든 재 시도 실패, address:{}, error:{}" ,address, e.getMessage());
        return null;
    }
}
