package org.delivery.takeout.domain.store.service

import org.assertj.core.util.Lists
import org.delivery.takeout.domain.store.cache.StoreRedisTemplateService
import org.delivery.takeout.domain.store.converter.StoreConverter
import org.delivery.takeout.domain.store.model.StoreDto
import spock.lang.Specification

class StoreSearchServiceTest extends Specification {

    private StoreSearchService storeSearchService

    private StoreRepositoryService storeRepositoryService = Mock()
    private StoreConverter storeConverter = Mock()
    private StoreRedisTemplateService storeRedisTemplateService = Mock()

    private List<StoreDto> storeDtoList

    def setup(){
        storeSearchService = new StoreSearchService(storeRepositoryService,storeConverter,storeRedisTemplateService)

        storeDtoList = Lists.newArrayList(
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
                        .build())
    }

    def "redis 실패 시, db에서 스토어 조회"(){
        when:
        storeRedisTemplateService.findAll() >> []
        storeRepositoryService.findAll() >> storeDtoList

        def result = storeSearchService.searchStoreDtoList()

        then:
        result.size() == 2
    }
}
