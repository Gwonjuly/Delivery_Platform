package org.delivery.account.domain.token.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenValidationResponse {
    private Long userId;
}
