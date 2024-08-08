package org.delivery.takeout.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreRepositoryService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<StoreEntity> findAll(){
        return storeRepository.findAll();
    }


}
