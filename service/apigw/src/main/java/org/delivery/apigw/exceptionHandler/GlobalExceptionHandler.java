package org.delivery.apigw.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("Global Error Exception url: {}", exchange.getRequest().getURI(), ex);

        var response = exchange.getResponse();
        if (response.isCommitted())
            return Mono.error(ex);

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return response.writeWith(
                Mono.fromSupplier(()->{
                    try {
                        var errorResponse = new ErrorResponse(ex.getLocalizedMessage());
                        var errorResponseByteArray  = objectMapper.writeValueAsBytes(errorResponse.getError());
                        var dataBuffer = response.bufferFactory();
                        return dataBuffer.wrap(errorResponseByteArray);
                    }catch (Exception e) {
                        throw new ApiException(ErrorCode.BAD_REQUEST);
                    }
                })
        );

    }
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ErrorResponse{
        private String error;
    }
}
