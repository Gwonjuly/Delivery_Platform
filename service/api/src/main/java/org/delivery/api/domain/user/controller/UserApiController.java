package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.UserSession;
import org.delivery.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
//차단된 API(OPEN과 달리 로그인을 해야만 사용할 수 있는 API)
public class UserApiController {

    private final UserBusiness userBusiness;

    //로그인한 사용자 호출
    @GetMapping("/me")
    public Api<UserResponse> me(@UserSession User user){

        //AuthorizationInterceptor에서 토큰 인증 후, 저장한 userId 호출
        /*var requestContext= Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        var userId=requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        var response=userBusiness.me(Long.parseLong(userId.toString()));
        -> UserSessionResolver*/
        var response=userBusiness.me(user.getId());
        return Api.OK(response);
        /**
         * RequestContext
         * 요청이 하나 들어올 때 마다 생성됨
         * 요청 -> 필터 -> 인터셉터 -> 컨트롤러 -> 응답 이 나갈 때 까지 유지되는 글로벌한 저장 영역 스레드 로컬
         * RequestContext는 어디서든 사용할 수 있기에 이 컨트롤러 말고 userBusiness.me에서도 사용 가능함
         *
         * UserSessionResolver
         * 1. @UserSession + User 클래스일 경우, UserSessionResolver 작동
         * 2. 리졸버에서 파라미터(User) 및 어노테이션(UserSession) 체크
         * 3. RequestContextHolder에서  헤더에 있는 Authorization-token GET
         * 4. 위 토큰을 사용해 등록되어 있던 userId GET
         * 5. userId를 통해 User build
         */
    }
}
