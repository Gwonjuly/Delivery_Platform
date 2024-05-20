package org.delivery.api.domain.userorder.producer;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.rabbitmq.Producer;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrderEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderProducer {

    private final Producer producer;

    private static final String EXCHANGE="delivery.exchange";
    private static final String ROUTE_KEY="delivery.key";

    /**
     * 1. 사용자 주문(UserOrderEntity)
     * 2. UserOrderEntity 애서 id 추출
     * 3. producer()로 id(msg)를 exchange 에 전송
     */
    public void sendOrder(UserOrderEntity userOrderEntity){
        sendOrder(userOrderEntity.getId());//user_order의 id
    }

    public void sendOrder(Long userOrderId){
        var message= UserOrderMessage.builder()
                .userOrderId(userOrderId)
                .build();
        producer.producer(EXCHANGE, ROUTE_KEY, message);
    }
}
