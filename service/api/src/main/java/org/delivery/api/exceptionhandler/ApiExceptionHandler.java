package org.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice//특정 패키지에서 발생한 예외 처리

@Order(value = Integer.MIN_VALUE)//가장 먼저 실행 min, 가장 나중에 실행 max(ApiException: 제일 먼저 실해됨)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiException){
        log.error("",apiException);//ApiException이 RuntimeException을 상속받았기에 stack trace 가능

        var errorCode=apiException.getErrorCodeIfs();
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiException.getErrorDescription())
                );
    }
}
