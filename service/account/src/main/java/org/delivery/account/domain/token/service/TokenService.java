package org.delivery.account.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.account.domain.token.ifs.TokenHelperIfs;
import org.delivery.account.domain.token.model.TokenDto;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public Long validationToken(String token){
        var map=tokenHelperIfs.validationTokenWithThrow(token);
        var userId=map.get("userId");
        Objects.requireNonNull(userId,()->{throw new ApiException(ErrorCode.NULL_POINT);
        });
        return Long.parseLong(userId.toString());
    }
}
