package org.delivery.takeout.domain.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.takeout.domain.store.cache.StoreRedisTemplateService;
import org.delivery.takeout.domain.store.model.StoreDto;
import org.delivery.takeout.domain.store.service.StoreRepositoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/take-out")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepositoryService storeRepositoryService;
    private final StoreRedisTemplateService storeRedisTemplateService;

    @GetMapping("/redis/save")
    public String save(){

        List<StoreDto> storeDtoList = storeRepositoryService.findAll().stream()
                .map(it -> StoreDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .latitude(it.getLatitude())
                        .longitude(it.getLongitude())
                        .address(it.getAddress())
                        .build())
                .collect(Collectors.toList());

        storeDtoList.forEach(storeRedisTemplateService::save);
        return "success";

    }
}
