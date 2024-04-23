package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;

@Business//Custom Annotation
@RequiredArgsConstructor
public class UserBusiness {

    //복잡한 비스니스 로직 처리, 여러군데에서 생성자 주입 가능
    private final UserService userService;
    private final UserConverter userConverter;
}
