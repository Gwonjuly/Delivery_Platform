package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.common.api.Api;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
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
    public Api<AccountMeResponse> me(){
        var response =AccountMeResponse.builder()
                .name("홍길동")
                .email("wnfl@naver.com")
                .registeredAt(LocalDateTime.now())
                .build();

        //RunTimeException Test
        var str="안녕하세요";
        int age=0;
        try {
            Integer.parseInt(str);
        }catch (Exception e){
            throw new ApiException(ErrorCode.SERVER_ERROR,e,"사용자 Me 호출 시 에러 발생");
        }
        return Api.OK(response);//Api의 body <T>는 AccountMeResponse 형태, swagger 결과: notion

        //return Api.ERROR(UserErrorCode.USER_NOT_FOUND,"홍길동이라는 사용자 없음");
        /*
    {
"result": {
"result_code": 1404,
"result_message": "사용자를 찾을 수 없음",
"result_description": "홍길동이라는 사용자 없음"
},
"body": null
}
         */

    }//swagger-ui: http://localhost:8080/swagger-ui/index.html, Schmas에 AccountMeResponse의 구조를 보여줌
}
