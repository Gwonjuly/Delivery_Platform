package org.delivery.account.domain.token.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.account.domain.token.model.TokenDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenValidationRequest {

    private TokenDto tokenDto;
}
