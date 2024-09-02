package org.delivery.storeadmin.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("Authorization Interceptor url: {}",request.getRequestURI());

        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        //js, html, png 같은 리소스를 요청하는 경우 = pass(통과)
        if(handler instanceof ResourceHttpRequestHandler)
            return true;

        var userId=request.getHeader("x-user-id");
        var email = request.getHeader("user-email");

        if(userId==null){
            throw new ApiException(ErrorCode.BAD_REQUEST,"x-user-id 헤더 없음");
        }

        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
        requestContext.setAttribute("user-email",email,RequestAttributes.SCOPE_REQUEST);
        return true;
    }
}
