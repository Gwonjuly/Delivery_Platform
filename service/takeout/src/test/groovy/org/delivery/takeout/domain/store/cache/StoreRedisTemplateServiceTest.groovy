package org.delivery.takeout.domain.store.cache

import org.delivery.takeout.AbstractContainerBaseTest
import org.delivery.takeout.domain.store.model.StoreDto
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
//통합 테스트
class StoreRedisTemplateServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private StoreRedisTemplateService storeRedisTemplateService

    def setup(){
        storeRedisTemplateService.findAll()
            .forEach(it->storeRedisTemplateService.delete(it.getId()))
    }

    def "Redis save success"(){
        given:
        String storeName = "에그몬"
        String storeAddress = "원주시 단계동"

        StoreDto storeDto = StoreDto.builder()
        .id(1L)
        .name(storeName)
        .address(storeAddress)
        .build()

        when:
        storeRedisTemplateService.save(storeDto)
        List<StoreDto> result = storeRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1L
    }
}
