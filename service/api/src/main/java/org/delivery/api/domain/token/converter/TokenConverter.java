package org.delivery.api.domain.token.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Converter;
import org.delivery.common.error.ErrorCode;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.model.TokenDto;
import org.delivery.common.exception.ApiException;

import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class TokenConverter {

    public TokenResponse toResponse(TokenDto accessToken, TokenDto refreshToken){
        //해당 토큰의 null 체크
        Objects.requireNonNull(accessToken,()->{throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken,()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpireAt(refreshToken.getExpiredAt())
                .build();
    }
}
