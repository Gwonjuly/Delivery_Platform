/*
package org.delivery.apigw.filter;

import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.TokenErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    public AuthorizationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            log.info("AuthorizationFilter route uri:{}",request.getURI());

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer","");

            return chain.filter(exchange);
        };
    }

    public static class Config{
    }
}
*/
