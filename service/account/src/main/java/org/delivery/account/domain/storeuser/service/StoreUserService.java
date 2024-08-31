package org.delivery.account.domain.storeuser.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;

    public StoreUserEntity login(String email, String password){
        return storeUserRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(email,password,StoreUserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
