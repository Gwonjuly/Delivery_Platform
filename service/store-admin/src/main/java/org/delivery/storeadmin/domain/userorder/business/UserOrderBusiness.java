package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final SseConnectionPool sseConnectionPool;

    /**
     * 주문이 들어오면
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 session 찾기
     * push 하기
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage){
        //주문 내역 찾기
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(()-> new RuntimeException("사용자 주문 내역 없음"));

        //user_order_menu 찾기

        //user_order_menu -> store_menu
        //연결된 세션 찾기(가게와 연결되어 있는 커넥션)
        //response
        //push
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        //주문 메뉴, 가격, 상태

        //스토어에게 push
    }
}
