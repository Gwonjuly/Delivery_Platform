package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreUserAuthorizationService implements UserDetailsService {

    private final StoreUserRepository storeUserRepository;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("account의 이메일: {}",username);
        var storeUserEntity = storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(username, StoreUserStatus.REGISTERED);
        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeUserEntity.get().getStoreId(), StoreStatus.REGISTERED);

        return storeUserEntity.map(it->{

            var userSession= UserSession.builder()
                    .userId(it.getId())
                    .password(it.getPassword())
                    .email(it.getEmail())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .registeredAt(it.getRegisteredAt())
                    .lastLoginAt(it.getLastLoginAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .storeId(storeEntity.get().getId())
                    .storeName(storeEntity.get().getName())
                    .build();

            return userSession;
        }).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
