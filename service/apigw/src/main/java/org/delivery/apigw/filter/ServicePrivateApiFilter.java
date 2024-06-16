package org.delivery.apigw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServicePrivateApiFilter extends AbstractGatewayFilterFactory<ServicePrivateApiFilter.Config> {

    public ServicePrivateApiFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            var uri = exchange.getRequest().getURI();
            log.info("service api private filter route uri: {}",uri);
            var mono = chain.filter(exchange);
            return mono;
        });
    }

    public static class Config {
    }
}

