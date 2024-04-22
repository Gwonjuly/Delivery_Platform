package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
인터셉터를 등록하기 위해서 Config 설정 필요
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {//ctrl + i

        //인터셉터로 어떤 url을 처리했는지 로그 기록
        log.info("Authorization Interceptor url: {}",request.getRequestURI());

        //OPTION: 웹, 크롬의 경우 GET, POST 등 API를 요청하기전에 OPTION이라는 API를 요청을 해서 해당 메소드를 지원하는지 체크하는 API
        if(HttpMethod.OPTIONS.matches(request.getMethod())){//httpmethod가 request의 get method와 일치하는가?
            return true;
        }

        //js, html, png 같은 리소스를 요청하는 경우 = pass(통과)
        if(handler instanceof ResourceHttpRequestHandler)
            return true;

        // TODO header 검증
        return true;//일단 통과 처리
    }
}
