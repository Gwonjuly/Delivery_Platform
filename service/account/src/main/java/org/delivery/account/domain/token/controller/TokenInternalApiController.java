package org.delivery.account.domain.token.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.account.domain.token.business.TokenBusiness;
import org.delivery.account.domain.token.controller.model.TokenValidationRequest;
import org.delivery.account.domain.token.controller.model.TokenValidationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal-api/token")
@RequiredArgsConstructor
@Slf4j
public class TokenInternalApiController {

    private final TokenBusiness tokenBusiness;

    @PostMapping("/validation")
    public TokenValidationResponse tokenValidation(@RequestBody TokenValidationRequest request){
        log.info("token validation init: {}",request);
        return tokenBusiness.tokenValidation(request.getTokenDto());
    }
}
