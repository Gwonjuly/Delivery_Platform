package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
//공개된 즉, 열려있는 API
//WebConfig에서 인터셉터 exclude 처리 되어 있음(로그인 인증 체크 안함)
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    //사용자 가입 요청 처리: (요청: user) (리턴: UserResponse)
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        var response=userBusiness.register(request.getBody());
        return Api.OK(response);

    }
}
