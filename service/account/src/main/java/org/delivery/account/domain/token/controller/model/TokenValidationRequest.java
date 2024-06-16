package org.delivery.account.domain.token.controller.model;

import lombok.Data;
import org.delivery.account.domain.token.model.TokenDto;

@Data
public class TokenValidationRequest {

    private TokenDto tokenDto;
}
