package org.delivery.account.domain.token.ifs;

import org.delivery.account.domain.token.model.TokenDto;

import java.util.Map;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String,Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data);

    Map<String,Object> validationTokenWithThrow(String token);
}
