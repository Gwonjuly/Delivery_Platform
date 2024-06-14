package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Business;
import org.delivery.common.error.ErrorCode;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.common.exception.ApiException;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {
    //issueToken, TokenValidation

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. userEntity에서 userId 추출
     * 2. userId를 이용해서 access/refresh 토큰 발행
     * 3. converter를 활용하여 토큰 -> tokenResponse
     */
    public TokenResponse issueToken(UserEntity userEntity){
        //userEntity의 null 여부 체크
        return Optional.ofNullable(userEntity)
                .map(ue->{
                    return ue.getId();
                })
                .map(userId->{
                    var accessToken=tokenService.issueAccessToken(userId);
                    var refreshToken=tokenService.issueRefreshToken(userId);
                    return tokenConverter.toResponse(accessToken,refreshToken);
                })//null일 경우
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    //토큰으로 헤더 인증, 반환 userId
    public Long validationAccessToken(String accessToken){
        var userId=tokenService.validationToken(accessToken);
        return userId;
    }
}
