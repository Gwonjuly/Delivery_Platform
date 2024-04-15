package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.CriteriaBuilder;
@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{//enum은 상속받을 수 없음


    OK(200,200,"성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),400,"잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),500,"서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(),512,"Null Point")
    ;
    //final: 변형이 있으면 안됨
    //errorCode는 HttpstatusCode와 일치할 수 있지만 없는 코드일 수  있음
   private final Integer httpStatusCode; //내부 에러 코드에 상응하는 http status 코드
    private final Integer errorCode; //내부 에러 코드
    private final String description;

   /*- > @Getter
    @Override
    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getDescription() {
        return this.description;
    }*/
}
