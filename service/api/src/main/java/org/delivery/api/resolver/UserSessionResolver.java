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
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserBusiness userBusiness;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        var annotation=parameter.hasParameterAnnotation(UserSession.class);//UserSession 어노테이션이 있는지 체크
        var parameterType=parameter.getParameterType().equals(User.class);//parameter의 타입이 User 클래스와 같은지 확인
        return (annotation&&parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //supportsParameter 통과(true) 후, 실행

        var requestContext= RequestContextHolder.getRequestAttributes();
        var userId=requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var user = userBusiness.getUserInfo(Long.parseLong(userId.toString()));

        return user;
    }
}
