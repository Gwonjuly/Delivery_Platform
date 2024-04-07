package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.db.account.AccountEntity;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;//JpaConfig: db(다른 패키지)에 있는 것을 빈으로 등록하기 위해서 Configuration 필요
    @GetMapping("/me")
    public AccountMeResponse me(){
        return AccountMeResponse.builder()
                .name("홍길동")
                .email("wnfl@naver.com")
                .registeredAt(LocalDateTime.now())
                .build();
    }
}
