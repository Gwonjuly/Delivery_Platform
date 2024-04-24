package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Converter//Custom Annotation
@RequiredArgsConstructor
public class UserConverter {

    //TODO UserRegisterRequest -> UserEntity
    public UserEntity toEntity (UserRegisterRequest request){

        return Optional.ofNullable(request)//request가 있으면
                .map(it ->{
                    //entity로 변환
                    return UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .address(request.getAddress())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT,"UserRegisterRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(it ->{
                    //userResponse로 변환
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .status(userEntity.getStatus())
                            .email(userEntity.getEmail())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"userEntity Null"));
    }
}
