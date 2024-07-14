package org.delivery.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoreErrorCode implements ErrorCodeIfs{

    USER_ORDER_NOT_FOUND(400, 3000,"사용자 주문 내역 없음"),
    ;

    private final Integer httpStatusCode; //내부 에러 코드에 상응하는 http status 코드
    private final Integer errorCode; //내부 에러 코드
    private final String description;
}
