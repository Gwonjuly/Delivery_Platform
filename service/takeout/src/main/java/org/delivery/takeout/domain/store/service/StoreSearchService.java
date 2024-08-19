package org.delivery.takeout.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.takeout.domain.store.cache.StoreRedisTemplateService;
import org.delivery.takeout.domain.store.converter.StoreConverter;
import org.delivery.takeout.domain.store.model.StoreDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreSearchService {

    private final StoreRepositoryService storeRepositoryService;
    private final StoreConverter storeConverter;
    private final StoreRedisTemplateService storeRedisTemplateService;

    public List<StoreDto> searchStoreDtoList(){

        //redis 조회
        List<StoreDto> storeDtoList = storeRedisTemplateService.findAll();
        if(!storeDtoList.isEmpty()){
            log.info("redis findAll success");
            return storeDtoList;
        }
        else
            log.info("redis fail");

        //redis 조회 실패
        return storeRepositoryService.findAll().stream()
                .map(it -> storeConverter.toStoreDto(it))
                .collect(Collectors.toList());
    }
}
