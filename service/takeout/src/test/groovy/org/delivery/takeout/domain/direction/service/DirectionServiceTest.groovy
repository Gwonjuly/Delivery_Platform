package org.delivery.takeout.domain.direction.service

import org.delivery.takeout.domain.kakao.model.DocumentDto
import org.delivery.takeout.domain.store.model.StoreDto
import org.delivery.takeout.domain.store.service.StoreSearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private StoreSearchService storeSearchService = Mock()

    private DirectionService directionService = new DirectionService(storeSearchService)

    private List<StoreDto> storeDtoList

    def setup(){
        storeDtoList = new ArrayList<>()
        storeDtoList.addAll(
                StoreDto.builder()
                        .id(10)
                        .address("강원도 원주시 원일로 209-1") //in 10km
                        .name("유가호")
                        .latitude(37.3554605)
                        .longitude(127.9442457)
                        .build(),
                StoreDto.builder()
                        .id(11)
                        .address("강원도 원주시 중앙로 32") //in 10km
                        .name("원주칼국수")
                        .latitude(37.3466986)
                        .longitude(127.9537132)
                        .build()
        )
    }

    def "buildDirectionList - 결과 값이 가까운 거리 순으로 정렬 되는지 확인"(){
        given:
        def addressName = "상훈 보쌈족발"
        double inputLatitude = 37.3491711
        double inputLongitude = 127.9483128

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        storeSearchService.searchStoreDtoList() >> storeDtoList
        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).getTargetStoreName() == "원주칼국수"
        result.get(1).getTargetStoreName() == "유가호"
    }

    def "buildDirectionList - 결과 값이 반경 10km 내인지 확인"(){
        given:
        storeDtoList.add(
                StoreDto.builder()
                        .id(652)
                        .address("강원도 원주시 지정면 월송석화로 522") // out 10 km
                        .name("심마니능이백숙")
                        .latitude(37.4083863)
                        .longitude(127.8392432)
                        .build()
        )

        def addressName = "상훈 보쌈족발"
        double inputLatitude = 37.3491711
        double inputLongitude = 127.9483128

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        storeSearchService.searchStoreDtoList() >> storeDtoList
        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).getTargetStoreName() == "원주칼국수"
        result.get(1).getTargetStoreName() == "유가호"

    }
}
