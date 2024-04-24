package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.api.exception.ApiException;

import java.util.Optional;

@Business//Custom Annotation
@RequiredArgsConstructor
public class UserBusiness {

    //복잡한 비스니스 로직 처리, 여러군데에서 생성자 주입 가능
    private final UserService userService;
    private final UserConverter userConverter;

    /**
     * 사용자에 대한 가입처리  로직
     * 1. reqeust -> entity
     * 2. entity를 save
     * 3. save된  entity -> response
     * 4. return response
     */
    public UserResponse register(UserRegisterRequest request) {

        var entity=userConverter.toEntity(request);
        var newEntity=userService.register(entity);
        var response=userConverter.toResponse(newEntity);
        return response;

        /*java 8 이상 람다식(함수형) 코딩 버전
        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"request null"));*/
    }
}
