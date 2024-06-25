package org.delivery.account.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.account.domain.token.ifs.TokenHelperIfs;
import org.delivery.account.domain.token.model.TokenDto;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * 토큰에 대한 도메인 로직 담당(Only Token)
 * userId를 받아 토큰 발행
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    //return userId
    public Long validationToken(String token){
        var map=tokenHelperIfs.validationTokenWithThrow(token);
        var userId=map.get("userId");
        //validationTokenWithThrow의 파싱을 통해 나온 값에 userId가 없을 경우,
        Objects.requireNonNull(userId,()->{throw new ApiException(ErrorCode.NULL_POINT);
        });
        return Long.parseLong(userId.toString());
    }
}
