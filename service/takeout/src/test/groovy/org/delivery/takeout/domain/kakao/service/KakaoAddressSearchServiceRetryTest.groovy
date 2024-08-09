package org.delivery.takeout.domain.kakao.service

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.delivery.takeout.AbstractContainerBaseTest
import org.delivery.takeout.domain.kakao.model.DocumentDto
import org.delivery.takeout.domain.kakao.model.KakaoApiResponse
import org.delivery.takeout.domain.kakao.model.MetaDto
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper


class KakaoAddressSearchServiceRetryTest extends AbstractContainerBaseTest{

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper objectMapper = new ObjectMapper()

    private String inputAddress = "강원도 원주시 무실로12번길 13" //상훈 보쌈 족발

    def setup(){
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        println(mockWebServer.port)
        println(mockWebServer.url("/"))
    }

    def cleanup(){
        mockWebServer.shutdown()
    }

    def "searchAddress - RuntimeException 발생 시, retry 확인"(){
        given:
        def metaDto = new MetaDto(1)
        def documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build()
        def expectedKakaoApiResponse = new KakaoApiResponse(metaDto, Arrays.asList(documentDto))
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))//첫번째 호출
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)//두번째 호출
                .addHeader(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(expectedKakaoApiResponse)))

        def kakaoApiResult = kakaoAddressSearchService.searchAddress(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddress(inputAddress) >> uri //buildUriByAddress의 2번 호출 확인
        kakaoApiResult.getMetaDto().totalCount == 1
        kakaoApiResult.getDocumentDtoList().size() == 1
        kakaoApiResult.getDocumentDtoList().get(0).getAddressName() == inputAddress
    }

    def "searchAddress - RuntimeException 발생 및 retry 실패 후, recover 메서드 실행 확인"(){
        given:
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def result = kakaoAddressSearchService.searchAddress(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddress(inputAddress) >> uri
        result == null;
    }
}
