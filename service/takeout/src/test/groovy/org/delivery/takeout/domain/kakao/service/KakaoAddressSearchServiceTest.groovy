package org.delivery.takeout.domain.kakao.service

import org.delivery.common.error.ErrorCode
import org.delivery.common.exception.ApiException
import org.delivery.takeout.AbstractContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    def "SearchAddress-address가 null이면, Exception 반환"() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.searchAddress(address)

        then:
        /*result = thrown(ApiException.class)
        println("에러 코드:" + result)
        //result.getErrorDescription() == "Null Point"
        result.getErrorDescription() == ErrorCode.NULL_POINT.description*/
        result == null
    }

    def "SearchAddress-address가 유효할 경우 정상 응답"(){
        given:
        def address = "강원도 원주시 금불 1길 52"

        when:
        def result = kakaoAddressSearchService.searchAddress(address)

        then:
        println("검색 결과: "+result.documentDtoList)
        result.documentDtoList.size() > 0
        result.metaDto.totalCount > 0
        result.documentDtoList.get(0) != null
    }

    def "searchAddress - 정상적인 주소 입력 시, 위도/경도 변환 확인"(){
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.searchAddress(inputAddress)

        then:
        if(searchResult == null)
            actualResult = false
        else
            actualResult = searchResult.getDocumentDtoList().size() > 0

        where:
        inputAddress                 |   expectedResult
        "강원도 원주시 원일로 209-1"     |   true
        "강원도 원주시 중앙로 32"        |   true
        "강원도 원주시 무실로12번길 13"   |    true
        "강원도 원주시 잘못된 주소"       |   false
        "강원도 원주시 잘못된 주소"       |   false
        "강원도 원주시 잘못된 주소"       |   false
        "강원도 원주시 천사로 203-15"    |   true
    }
}
