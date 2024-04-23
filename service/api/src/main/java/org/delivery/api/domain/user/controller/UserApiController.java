package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.user.business.UserBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
//차단된 API(OPEN과 달리 로그인을 해야만 사용할 수 있는 API)
public class UserApiController {

    private final UserBusiness userBusiness;
}
