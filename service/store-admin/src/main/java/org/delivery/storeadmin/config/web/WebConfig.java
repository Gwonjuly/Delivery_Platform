package org.delivery.storeadmin.config.web;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.interceptor.AuthorizationInterceptor;
import org.delivery.storeadmin.resolver.UserSessionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;
    private final UserSessionResolver userSessionResolver;// //

    private List<String> OPEN_API=List.of("/open-api/**");

    private List<String> DEFAULT_EXCLUDE=List.of(
            "/",//root
            "favicon.ico",//아이콘 받아가는 것
            "/error"//에러도 겁증에서 제외
    );

    //swagger도 검증에서 제외
    private List<String> SWAGGER=List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns(DEFAULT_EXCLUDE)
                .excludePathPatterns(SWAGGER)
                ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionResolver);
    }
}
