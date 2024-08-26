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
    USER_NAME_DUPLICATED(400,1405,"사용자 이름 중복"),
    USER_PW_INCONSISTENCY(400,1406,"잘못된 비밀번호 입력"),
    ;
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
