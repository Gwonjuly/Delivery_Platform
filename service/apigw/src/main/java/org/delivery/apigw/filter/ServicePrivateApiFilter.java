package org.delivery.apigw.filter;

import lombok.extern.slf4j.Slf4j;
import org.delivery.apigw.account.model.TokenDto;
import org.delivery.apigw.account.model.TokenValidationRequest;
import org.delivery.apigw.account.model.TokenValidationResponse;
import org.delivery.common.error.TokenErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class ServicePrivateApiFilter extends AbstractGatewayFilterFactory<ServicePrivateApiFilter.Config> {

    public ServicePrivateApiFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var uri = exchange.getRequest().getURI();
            log.info("service api private filter route uri: {}",uri);
            //account server를 통한 인증 실행
            // 1. 토큰의 유무 확인
            var headers = exchange.getRequest().getHeaders().getOrEmpty("authorization-token");

            String token;
            if(headers.isEmpty()){
                throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
            }else{
                token = headers.get(0);
            }
            log.info("authorization-token: {}",token);

            // 2. 토큰 유효성 확인 및 account server 호출
            var accountApiUrl = UriComponentsBuilder //검증을 요청할 주소
                    .fromUriString("http://localhost")
                    .port(8082)
                    .path("/internal-api/token/validation")
                    .build()
                    .encode()
                    .toUriString();

            var webClient = WebClient.builder().baseUrl(accountApiUrl).build();

                // account 에서 검증할 토큰
            var request = TokenValidationRequest.builder()
                    .tokenDto(TokenDto.builder().token(token).build())
                    .build();
            log.info("request: {}", request);
            System.out.println("request:"+ request);

            return webClient //토큰 검증
                    .post()
                    .body(Mono.just(request), new ParameterizedTypeReference<TokenValidationRequest>() {})
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> response.bodyToMono(
                            new ParameterizedTypeReference<Object>() {})
                            .flatMap(error -> {
                                log.error("response error? :{}",error);
                                return Mono.error(new ApiException(TokenErrorCode.TOKEN_EXCEPTION));
                            }))
                    .bodyToMono(new ParameterizedTypeReference<TokenValidationResponse>() {})
                    .flatMap(response -> {
                        //응답이 왔을 경우 (사용자 인증 통과)
                         log.info("response: {}", response);

                         // 3. 사용자 정보 추가(프록시를 지날 때, 데이터 주입)
                        var userId = response.getUserId().toString();

                            //proxy request 생성
                        var proxyRequest = exchange.getRequest().mutate()
                                .header("x-user-id",userId)//헤더에 userId 추가
                                .build();

                        var requestBuild = exchange.mutate().request(proxyRequest).build();
                        var mono = chain.filter(requestBuild); //API 서버로 보냄
                        return mono;
                    })
                    .onErrorMap(e-> {
                        log.error("",e);
                        return e;
                    });
        };
    }

    public static class Config {
    }
}

