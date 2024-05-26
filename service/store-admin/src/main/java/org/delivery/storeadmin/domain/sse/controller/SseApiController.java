package org.delivery.storeadmin.domain.sse.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {

    private static final Map<String, SseEmitter> userConnection = new ConcurrentHashMap<>();
    private final SseConnectionPool sseConnectionPool;

    @GetMapping(path="/connect",produces = MediaType.TEXT_EVENT_STREAM_VALUE) //, 제공하는 미디어 타입
    public ResponseBodyEmitter connect(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        log.info("login user {}",userSession);

        var emitter = new SseEmitter(1000L * 60); //클라이언트와 연결된 SSE 연결 성립. ms 단위롤 timeout 시간 설정(기본: 30초)
        userConnection.put(userSession.getUserId().toString(),emitter);

        var temp = UserSseConnection.connect(userSession.getStoreId().toString(), sseConnectionPool);

        //sse 연결 후, callback 메소드의 역할은
        emitter.onTimeout(()->{
            log.info("on Timeout");
            //클라이언트와 타임아웃이 일어났을 때, "연결 종료" 호출
            emitter.complete();
        });

        //클라이언트와 연결이 종료될 때,
        emitter.onCompletion(()->{
            log.info("completion");
            userConnection.remove(userSession.getUserId().toString());
        });

        //최초 연결 시, 응답 전송
        var event = SseEmitter
                .event() //event 메소드 전송
                .name("onopen"); //로그인 시, sse connection 출력
                //.data("connect");//최초 연결 시, 데이터를 보내거나 받지 못해서 삭제

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    //메시지 보내기
    @GetMapping("/push-event")
    public void pushEvent(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        //기존에 연결된 유저 찾기
        var emitter = userConnection.get(userSession.getUserId().toString());

        var event=SseEmitter
                .event()
                .data("hello");//자동으로 onmessage에 전달
                //swagger로 메시지 보내기
        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
