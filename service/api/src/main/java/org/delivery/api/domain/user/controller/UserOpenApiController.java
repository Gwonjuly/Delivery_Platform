package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.user.business.UserBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
//공개된 즉, 열려있는 API
//WebConfig에서 인터셉터 exclude 처리 되어 있음(로그인 인증 체크 안함)
public class UserOpenApiController {

    private final UserBusiness userBusiness;
}
