package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.TokenErrorCode;
import org.delivery.api.domain.token.business.TokenBusiness;
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

/*
인터셉터를 등록하기 위해서 Config 설정 필요
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

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

        var userId=request.getHeader("x-user-id");
        if(userId==null){
            throw new ApiException(ErrorCode.BAD_REQUEST,"x-user-id 헤더 없음");
        }

        //검증 완료 후, userId 저장함 어디에? requestContext: Local Thread로 하나의 리퀘스트에 대해 유효하게 글로벌하게 저장할 수 있는 영역
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);//requestContext 영역에 리퀘스트 단위로 userId 저장

        return true;

        /** 인터셉터 역할
         * 1. authorization-token GET 후 검증
         * 2. RequestContextHolder를 통해 SCOPE_REQUEST에 userId 등록
         */
    }
}
