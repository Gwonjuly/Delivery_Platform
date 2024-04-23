package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor//생성자(DI 의존성) 주입
@Service
//컨트롤러 → 서비스 → 데이터 베이스,컨트롤러가 서비스에게 요청,
//서비스는 데이터베이스 레파지토리를 통해 특정한 데이터를 처리
public class UserService {
}
