package org.delivery.api.domain.user.business;

import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.common.error.UserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserBusinessTest {

    @MockBean private UserBusiness userBusiness;
    @MockBean private UserConverter userConverter;
    @MockBean private UserService userService;
    @MockBean private TokenBusiness tokenBusiness;

    @Test
    void 회원가입_정상동작() {
        //given
        String name = "julee";
        String email = "julee@example.com";
        String address = "원주시";
        String password = "123456";
        var request = new UserRegisterRequest(name, email, address, password);

        var userEntity = mock(UserEntity.class);
        var userResponse = mock(UserResponse.class);

        when(userConverter.toEntity(request)).thenReturn(userEntity);
        when(userService.register(any())).thenReturn(userEntity);
        when(userConverter.toResponse(new UserEntity())).thenReturn(userResponse);

        Assertions.assertDoesNotThrow(()->userBusiness.register(request));
    }

    @Test
    void 회원가입_이메일_중복(){
        String name = "julee";
        String email = "julee@example.com";
        String address = "원주시";
        String password = "123456";
        var request = new UserRegisterRequest(name, email, address, password);
        var userEntity = mock(UserEntity.class);

        when(userConverter.toEntity(request)).thenReturn(userEntity);
        when(userService.register(any())).thenThrow(new ApiException(UserErrorCode.USER_NAME_DUPLICATED));

        ApiException e = Assertions.assertThrows(ApiException.class, ()->userService.register(userEntity));
        Assertions.assertEquals(UserErrorCode.USER_NAME_DUPLICATED, e.getErrorCodeIfs());
    }

    @Test
    void 로그인_정상동작() {
        //given
        String email = "julee@example.com";
        String password = "123456";
        var request = new UserLoginRequest(email, password);

        var userEntity = mock(UserEntity.class);
        var tokenResponse = mock(TokenResponse.class);

        when(userService.login(email,password)).thenReturn(userEntity);
        when(tokenBusiness.issueToken(userEntity)).thenReturn(tokenResponse);

        Assertions.assertDoesNotThrow(()->userBusiness.login(request));
    }

    @Test
    void 로그인_유저정보_없음() {
        //given
        String email = "julee@example.com";
        String password = "123456";

        when(userService.login(email,password)).thenThrow(new ApiException(UserErrorCode.USER_NOT_FOUND));

        ApiException e = Assertions.assertThrows(ApiException.class, ()->userService.login(email,password));
        Assertions.assertEquals(UserErrorCode.USER_NOT_FOUND, e.getErrorCodeIfs());
    }
}