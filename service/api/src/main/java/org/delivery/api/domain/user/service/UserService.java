package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.UserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor//생성자(DI 의존성) 주입
@Service
//컨트롤러 → 서비스 → 데이터 베이스,컨트롤러가 서비스에게 요청,
//서비스는 데이터베이스 레파지토리를 통해 특정한 데이터를 처리
public class UserService {

    private final UserRepository userRepository;//JpaConfig로 인해 타 패키지(db)의 빈 등록

    public UserEntity register(UserEntity userEntity){

        return Optional.ofNullable(userEntity)//userEntity가 있으면
                .map(it->{
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());

                    return userRepository.save(userEntity);
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"UserEntity Null"));
    }

    public UserEntity login(String email,String password){
        var entity=getUserWithThrow(email, password);
        return entity;
    }

    public UserEntity getUserWithThrow(String email, String password){
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(email,password, UserStatus.REGISTERED)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));//사용자가  없으면 throw 발생
    }

    public UserEntity getUserWithThrow(Long userId){
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(userId,UserStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
