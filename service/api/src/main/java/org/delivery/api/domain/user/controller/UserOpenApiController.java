package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
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
    //Api<UserRegisterRequest> 형식
    /*
    {
  "result": {
    "result_code": 0,
    "result_message": "string",
    "result_description": "string"
  },
  "body": { =  response
    "id": 0,
    "name": "string",
    "email": "string",
    "status": "REGISTERED",
    "address": "string",
    "registered_at": "2024-04-24T13:31:47.127Z",
    "unregistered_at": "2024-04-24T13:31:47.127Z",
    "last_login_at": "2024-04-24T13:31:47.127Z"
  }
}
     */

    //로그인
    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid
            @RequestBody Api<UserLoginRequest> request
    ){
        var response=userBusiness.login(request.getBody());//Api<>가 result+body로 구성
        return Api.OK(response);
    }
    /* 로그인 시, 발행되는 토큰
    {
  "result": {
    "result_code": 200,
    "result_message": "성공",
    "result_description": "성공"
  },
  "body": {
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImV4cCI6MTcxNDQwNDU0Mn0.9kCKb0kLWvck6m6-oiroyW9adNIpjAvclduDWIWl97U",
    "access_token_expired_at": "2024-04-30T00:29:02.6216458",
    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VJZCI6MSwiZXhwIjoxNzE0NDQ0MTQyfQ.p1AA_8zfEMqfsCQiKHFqaXbFriKCIcNdX8Nx0jKYmhQ",
    "refresh_token_expire_at": "2024-04-30T11:29:02.6492637"
  }
}
     */
}
