package org.delivery.storeadmin.domain.storeuser.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.common.exception.ApiExceptionIfs;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public StoreUserEntity register(StoreUserEntity storeUserEntity){
        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));//password를 인코딩하여 값 setting
        storeUserEntity.setRegisteredAt(LocalDateTime.now());
        return storeUserRepository.save(storeUserEntity);
    }

    public Optional<StoreUserEntity> getRegisterUser(String email){
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);
    }

    public StoreUserEntity getUserWithThrow(Long userId){
        return storeUserRepository.findFirstByIdAndStatusOrderByIdDesc(userId, StoreUserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
