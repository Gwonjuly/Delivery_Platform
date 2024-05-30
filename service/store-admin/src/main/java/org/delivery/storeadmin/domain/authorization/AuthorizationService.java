package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storeuser.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 웹에서 입력한 username(email)로 사용자가 있는지크
        var storeUserEntity=storeUserService.getRegisterUser(username);
        var storeEntity=storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeUserEntity.get().getStoreId(), StoreStatus.REGISTERED);

        return storeUserEntity.map(it->{// 사용자가 있으면 storeUserEntity의 email과 pw를 User(security 제공)에 담아 리턴
            //로그인 시, 저장되는 사용자 정보 확장
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

            return userSession; //UserSession은 UserDetails 상속 받음
        }).orElseThrow(()->new UsernameNotFoundException(username));
        /*
        Spring-security:
        UserDatils에 담긴 storeUserEntity의 pw와 웹에서 입력한 pw를 비교
        storeUserEntity의 pw:  회원가입 시, hash로 encoding이 되어 db에 저장되어 있는 상태
        웹에서 입력한 pw: BCrypt로 hash함
        위 둘 비교 후, 같으면 main.html로 이동
         */
    }
}
