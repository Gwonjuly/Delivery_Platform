package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Business;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;

@Business//Custom Annotation
@RequiredArgsConstructor
public class UserBusiness {

    //복잡한 비스니스 로직 처리, 여러군데에서 생성자 주입 가능
    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    /**
     * 사용자에 대한 가입처리  로직
     * 1. reqeust -> entity
     * 2. entity를 save
     * 3. save된  entity -> response
     * 4. return response
     */
    public UserResponse register(UserRegisterRequest request) {

        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userConverter.toResponse(newEntity);
        return response;

        /*java 8 이상 람다식(함수형) 코딩 버전
        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"request null"));*/
    }

    /**
     * 로그인 로직
     * 1. email과 password를 가지고 사용자(name) 체크
     * 2. user entity 로그인 확인
     * 3. token 생성
     * 4. token response
     */
    public TokenResponse login(UserLoginRequest body) {
        var userEntity = userService.login(body.getEmail(), body.getPassword());
        //사용자 없으면 throw

        //사용자 있으면 토큰 생성
        var tokenResponse=tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }

    public UserResponse me(Long userId) {
        var userEntity=userService.getUserWithThrow(userId);
        var response=userConverter.toResponse(userEntity);
        return response;
    }
}
