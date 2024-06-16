package org.delivery.account.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.account.domain.token.controller.model.TokenValidationResponse;
import org.delivery.account.domain.token.model.TokenDto;
import org.delivery.account.domain.token.service.TokenService;
import org.delivery.common.annotation.Business;

@Business
@RequiredArgsConstructor
public class TokenBusiness {
    private final TokenService tokenService;

    public TokenValidationResponse tokenValidation(TokenDto tokenDto){
        var result = tokenService.validationToken(tokenDto.getToken());
        return TokenValidationResponse.builder()
                .userId(result)
                .build();
    }
}
