package org.delivery.takeout.domain.kakao.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService

    def setup(){
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    def "BuildUriBySearchAddress - 한글 주소 입력 시, 정상 인코딩"() {
        given:
        String address = "강원도 원주시"
        def charset = StandardCharsets.UTF_8

        when:
        def encodeUri = kakaoUriBuilderService.buildUriByAddress(address)
        def decodeResult = URLDecoder.decode(encodeUri.toString(),charset)

        then:
        print(encodeUri)
        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=강원도 원주시"
    }
}
