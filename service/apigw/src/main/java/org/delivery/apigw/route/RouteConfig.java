package org.delivery.apigw.route;

import lombok.RequiredArgsConstructor;
import org.delivery.apigw.filter.ServicePrivateApiFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {
    private final ServicePrivateApiFilter servicePrivateApiFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route( spec -> spec
                        .order(-1) //우선순위
                        .path("/service-api/api/**") //매칭할 주소
                        .filters(filterSpec -> filterSpec
                                .filter(servicePrivateApiFilter.apply(new ServicePrivateApiFilter.Config())) //필터 지정
                                .rewritePath("/service-api(?<segment>/?.*)", "${segment}"))
                        .uri("http://localhost:8080")//라우팅 할 주소
                )
                .route(spec -> spec
                        .order(-1)
                        .path("/service-takeout/api/**")
                        .filters(filterSpec ->filterSpec
                                .filter(servicePrivateApiFilter.apply(new ServicePrivateApiFilter.Config()))
                                .rewritePath("/service-takeout(?<segment>/?.*)", "${segment}"))
                        .uri("http://localhost:8083")
                )
                .build();
    }
}
