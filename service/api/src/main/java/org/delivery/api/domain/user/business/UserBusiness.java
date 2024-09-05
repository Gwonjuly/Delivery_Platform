package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.user.UserCache;
import org.delivery.api.domain.user.model.User;
import org.delivery.common.annotation.Business;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;
    private final UserCache userCache;

    @Transactional
    public UserResponse register(UserRegisterRequest request) {

        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userConverter.toResponse(newEntity);
        return response;
    }

    @Transactional
    public TokenResponse login(UserLoginRequest body) {
        var userEntity = userService.login(body.getEmail(), body.getPassword());
        userCache.setUser(userConverter.toUser(userEntity));

        var tokenResponse=tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }

    @Transactional(readOnly = true)
    public UserResponse me(Long userId) {
        var user = getUserInfo(userId);
        var response = userConverter.toResponse(user);;
        return response;
    }

    public User getUserInfo(Long userId){
         return userCache.getUser(userId).orElseGet(
                 ()-> userConverter.toUser(userService.getUserWithThrow(userId))
         );
    }
}
