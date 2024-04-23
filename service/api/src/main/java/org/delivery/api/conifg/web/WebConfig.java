package org.delivery.api.conifg.web;

import lombok.RequiredArgsConstructor;
import org.delivery.api.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;//이거 component 처리되어 있음

    //open-api로 시작하는 모든 주소, 해당되는 주소는 검증하지 않음
    private List<String> OPEN_API=List.of("/open-api/**");

    //기본적오르 검증에서 제외해야할 주소
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

    @Override//인터셉터 등록(인증(로그인된 사용자) 체크)
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(authorizationInterceptor)//해당 인터셉터 등록
                .excludePathPatterns(OPEN_API)//open_api는 검증하지 않음
                .excludePathPatterns(DEFAULT_EXCLUDE)
                .excludePathPatterns(SWAGGER)
                ;
    }
}
