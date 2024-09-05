package org.delivery.takeout.domain.store.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.takeout.domain.store.model.StoreDto;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreRedisTemplateService {

    private static final String CACHE_KEY = "STORE";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(StoreDto storeDto){
        if(Objects.isNull(storeDto) || Objects.isNull(storeDto.getId())){
            throw new ApiException(ErrorCode.NULL_POINT,"StoreDto is null");
        }

        try{
            hashOperations.put(CACHE_KEY,
                    storeDto.getId().toString(),
                    serializeStoreDto(storeDto));
            log.info("StoreRedisTemplate save success",storeDto.getId());
        }catch (Exception e){
            log.error("StoreRedisTemplate save error",e.getMessage());
        }
    }

    public List<StoreDto> findAll(){
        try {
            List<StoreDto> storeDtoList = new ArrayList<>();
            for(String value : hashOperations.entries(CACHE_KEY).values()){
                StoreDto storeDto = deserializeStoreDto(value);
                storeDtoList.add(storeDto);
            }
            return storeDtoList;
        }catch (Exception e){
            log.error("StoreRedisTemplate findAll error",e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id){
        hashOperations.delete(CACHE_KEY,String.valueOf(id));
        log.info("StoreRedisTemplate delete id:{}",id);
    }

    private String serializeStoreDto(StoreDto storeDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(storeDto);
    }

    private StoreDto deserializeStoreDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, StoreDto.class);
    }
}
