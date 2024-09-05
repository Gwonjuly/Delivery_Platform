package org.delivery.takeout.domain.kakao.service

import org.delivery.db.store.StoreEntity
import org.delivery.db.store.StoreRepository
import org.delivery.db.store.enums.StoreCategory
import org.delivery.db.store.enums.StoreStatus
import org.delivery.takeout.AbstractContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class repositoryTest extends AbstractContainerBaseTest{

    @Autowired
    private StoreRepository storeRepository

    def "save test"(){

        given:
        String name = "에그몬"
        String address = "원주시"
        StoreStatus status = StoreStatus.REGISTERED
        StoreCategory category = StoreCategory.HAMBURGER
        double star = 3
        String thumbnailUrl = "!!"
        BigDecimal minimumAmount = 130
        BigDecimal minimumDeliveryAmount = 150
        String phoneNumber = "010"
        double latitude = 13
        double longitude = 15

        def storeEntity = StoreEntity.builder()
            .name(name)
            .address(address)
            .status(status)
            .category(category)
            .star(star)
            .thumbnailUrl(thumbnailUrl)
            .minimumAmount(minimumAmount)
            .minimumDeliveryAmount(minimumDeliveryAmount)
            .phoneNumber(phoneNumber)
            .latitude(latitude)
            .longitude(longitude)
            .build()

        when:
        def result = storeRepository.save(storeEntity)

        then:
        result.getAddress() == address
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }
}
