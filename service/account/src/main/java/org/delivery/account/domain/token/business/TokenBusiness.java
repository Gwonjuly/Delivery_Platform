package org.delivery.account.domain.token.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.account.domain.token.controller.model.TokenValidationRequest;
import org.delivery.account.domain.token.controller.model.TokenValidationResponse;
import org.delivery.account.domain.token.converter.TokenConverter;
import org.delivery.account.domain.token.model.TokenResponse;
import org.delivery.account.domain.token.service.TokenService;
import org.delivery.common.annotation.Business;
import org.delivery.db.storeuser.StoreUserRepository;

@Business
@RequiredArgsConstructor
@Slf4j
public class TokenBusiness {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;
    private final StoreUserRepository storeUserRepository;

    public TokenResponse issueToken(Long userId){

        var accessToken= tokenService.issueAccessToken(userId);
        var refreshToken= tokenService.issueRefreshToken(userId);
        return tokenConverter.toResponse(accessToken,refreshToken);
    }

    public TokenValidationResponse tokenValidation(TokenValidationRequest request){
        log.info("token dto_business: {}", request.getTokenDto());
        var result = tokenService.validationToken(request.getTokenDto().getToken());
        var email = storeUserRepository.findById(result).get().getEmail();
        return TokenValidationResponse.builder()
                .userId(result)
                .email(email)
                .build();
    }
}
