package org.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.common.api.Api;
import org.delivery.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice//특정 패키지에서 발생한 예외 처리
/*
exception의 global 및 restapi의 핸들러의 우선순위 결정(:order)
다른 핸들러를 통과하고 도착하면 그것에 대한 예외처리 진행(가장 마지막에 실행)
 */
@Order(value = Integer.MAX_VALUE)//가장 먼저 실행 min, 가장 나중에 실행 max(글로벌: 가장  마지막에 실행될 최후의 수단)
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)//예외들 중에서 Exception이 최상위 클래스임=예측하지 못한 발생 에러를 여기서 처리하겠다
    public ResponseEntity<Api<Object>> exception(Exception exception){//Exception은 스프링이 주입해줌
        log.error("",exception);

        return ResponseEntity
                .status(500)
                .body(Api.ERROR(ErrorCode.SERVER_ERROR));
    }
}
