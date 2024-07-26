package org.delivery.api.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.domain.user.UserCache;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.common.annotation.UserSession;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
/**
 * HandlerMethodArgumentResolver
 * request가 들어오면 실행 (AOP 방식)
 */
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserBusiness userBusiness;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //지원하는 파라미터 체크, 어노테이션 체크

        var annotation=parameter.hasParameterAnnotation(UserSession.class);//UserSession 어노테이션이 있는지 체크
        var parameterType=parameter.getParameterType().equals(User.class);//parameter의 타입이 User 클래스와 같은지 확인인
        return (annotation&&parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //supportsParameter 통과(true) 후, 실행

        //RequestContextHolder에서 찾아오기 -> 유저정보 대입
        var requestContext= RequestContextHolder.getRequestAttributes();
        var userId=requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var user = userBusiness.getUserInfo(Long.parseLong(userId.toString()));

        return user;
    }
    /**리졸버 역할
     * 1. 어노테이션 및 클래스 체크
     * 2. (@UserSession && User)일 경우, 리졸버 동작
     * 3. Authorization 인터셉터를 통해 등록된 userId 찾기
     * 4. userId를 통해 User build
     */
}
