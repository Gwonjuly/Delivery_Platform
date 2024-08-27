package org.delivery.storeadmin.domain.sse.connection.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoosIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;

    private final SseEmitter sseEmitter;

    private final ConnectionPoosIfs<String, UserSseConnection> connectionPoosIfs;

    private final ObjectMapper objectMapper;

    //기본  생성자
    private UserSseConnection(
            String uniqueKey, ConnectionPoosIfs<String,
            UserSseConnection> connectionPoosIfs,
            ObjectMapper objectMapper){

        //key 초기화
        this.uniqueKey=uniqueKey;

        //emitter 초기화
        this.sseEmitter=new SseEmitter(1000L * 60);

        //ConnectionPoosIfs 초기화
        this.connectionPoosIfs = connectionPoosIfs;

        //object mapper 초기화
        this.objectMapper = objectMapper;

        //on completion
        this.sseEmitter.onCompletion(()->{
            //연결 종료 시, 제거
            this.connectionPoosIfs.onCompletionCallback(this);
        });

        //on timeout
        this.sseEmitter.onTimeout(()->{
            this.sseEmitter.complete();
        });

        //emitter 초기화 후, onopen 필수
        this.sendMessage("onopen", "connect");
    }

    public static UserSseConnection connect(
            String uniqueKey,
            ConnectionPoosIfs<String, UserSseConnection> connectionPoosIfs,
            ObjectMapper objectMapper){
        return new UserSseConnection(uniqueKey, connectionPoosIfs, objectMapper);
    }

    public void sendMessage(String eventName, Object data) {

        try {
            var json = this.objectMapper.writeValueAsString(data);

            var event = SseEmitter.event()
                    .name(eventName)
                    .data(json);

            this.sseEmitter.send(event);

        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .data(json);
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
