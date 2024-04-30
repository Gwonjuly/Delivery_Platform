package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토큰 에러일 경우 2000번대 코드 사용
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{

    INVALID_TOKEN(400,2000,"유효하지 않은 토큰"),
    EXPIRED_TOKEN(400,2001,"만료된 토큰"),
    TOKEN_EXCEPTION(400,2002,"토큰 알 수 없는 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(400,2003,"인증 헤더 토큰 없음"),
    ;
    //final: 변형이 있으면 안됨
    //errorCode는 HttpstatusCode와 일치할 수 있지만 없는 코드일 수  있음
    private final Integer httpStatusCode; //내부 에러 코드에 상응하는 http status 코드
    private final Integer errorCode; //내부 에러 코드
    private final String description;
}
