package org.delivery.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * 유저 에러일 경우 1000번대 코드 사용
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs{

    USER_NOT_FOUND(400,1404,"사용자를 찾을 수 없음"),
    ;
    //final: 변형이 있으면 안됨
    //errorCode는 HttpstatusCode와 일치할 수 있지만 없는 코드일 수  있음
    private final Integer httpStatusCode; //내부 에러 코드에 상응하는 http status 코드
    private final Integer errorCode; //내부 에러 코드
    private final String description;
}
