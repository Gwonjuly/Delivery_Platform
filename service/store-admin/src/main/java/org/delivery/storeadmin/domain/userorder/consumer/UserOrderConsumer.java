package org.delivery.storeadmin.domain.userorder.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.message.model.UserOrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component //service 무관
@RequiredArgsConstructor
@Slf4j
public class UserOrderConsumer {

    @RabbitListener(queues = "delivery.queue" )//어떤 queue에서 메시지를  받아올 것인지
    public void userOrderConsumer(
            UserOrderMessage userOrderMessage //UserOrderProducer에서 build됨
    ){
        log.info("message queue>> {}",userOrderMessage);
    }
}
