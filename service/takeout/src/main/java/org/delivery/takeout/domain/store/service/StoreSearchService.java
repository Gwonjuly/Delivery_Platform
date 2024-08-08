package org.delivery.takeout.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<StoreDto> searchStoreDtoList(){
        return storeRepositoryService.findAll().stream()
                .map(it -> storeConverter.toStoreDto(it))
                .collect(Collectors.toList());
    }
}
