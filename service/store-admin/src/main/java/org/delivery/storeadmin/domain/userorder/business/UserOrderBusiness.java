package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final SseConnectionPool sseConnectionPool;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final UserOrderConverter userOrderConverter;

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
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        //user_order_menu -> store_menu
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity->{
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()); //storeMenuEntityList
                })
                .map(storeMenuEntity->{
                    return storeMenuConverter.toResponse(storeMenuEntity);
                })
                .collect(Collectors.toList());

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();

        //연결된 세션 찾기(가게와 연결되어 있는 커넥션)
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        //스토어에게 push
        userConnection.sendMessage(push);
    }
}
