package org.delivery.storeadmin.domain.sse.connection.model;

import lombok.Data;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoosIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Data
public class UserSseConnection {

    private String uniqueKey;

    private SseEmitter sseEmitter;

    private ConnectionPoosIfs<String, UserSseConnection> connectionPoosIfs;

    //기본  생성자
    private UserSseConnection(
            String uniqueKey, ConnectionPoosIfs<String,
            UserSseConnection> connectionPoosIfs){

        //key 초기화
        this.uniqueKey=uniqueKey;

        //emitter 초기화
        this.sseEmitter=new SseEmitter(1000L * 60);

        //ConnectionPoosIfs 초기화
        this.connectionPoosIfs = connectionPoosIfs;

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
            ConnectionPoosIfs<String, UserSseConnection> connectionPoosIfs){
        return new UserSseConnection(uniqueKey, connectionPoosIfs);
    }

    public void sendMessage(String eventName, Object data){
        var event = SseEmitter.event()
                .name(eventName)
                .data(data);
        try {
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }


    }
}
