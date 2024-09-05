package org.delivery.storeadmin.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.StoreUserAuthorizationService;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storeuser.business.StoreUserBusiness;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final StoreUserBusiness storeUserBusiness;
    private final StoreUserAuthorizationService storeUserAuthorizationService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        var annotation=parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
        var parameterType=parameter.getParameterType().equals(UserSession.class);
        return (annotation&&parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        var requestContext= RequestContextHolder.getRequestAttributes();
        var userId=requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var email=requestContext.getAttribute("user-email", RequestAttributes.SCOPE_REQUEST);

        return storeUserAuthorizationService.loadUserByUsername(email.toString());

    }
}
