package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.StoreErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderRequest;
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

    public void pushUserOrder(UserOrderMessage userOrderMessage){
        //주문 내역 찾기
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(()-> new RuntimeException("사용자 주문 내역 없음"));

        //user_order_menu 찾기
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        //user_order_menu -> store_menu
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity->{
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenu().getId()); //storeMenuEntityList
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
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStore().getId().toString());

        //스토어에게 push
        userConnection.sendMessage(push);
    }

    public UserOrderDetailResponse accept(UserOrderRequest userOrderRequest){
        var userOrderEntity = userOrderService.getUserOrder(userOrderRequest.getUserOrderResponse().getId())
                .orElseThrow(()-> new ApiException(StoreErrorCode.USER_ORDER_NOT_FOUND));
        var newUserOrderEntity = userOrderService.accept(userOrderEntity);
        var response = userOrderConverter.toResponse(newUserOrderEntity);
        return UserOrderDetailResponse.builder()
                .userOrderResponse(response)
                .storeMenuResponseList(userOrderRequest.getStoreMenuResponseList())
                .build();
    }

    public UserOrderDetailResponse cooking(UserOrderRequest userOrderRequest){
        var userOrderEntity = userOrderService.getUserOrder(userOrderRequest.getUserOrderResponse().getId())
                .orElseThrow(()->new ApiException(StoreErrorCode.USER_ORDER_NOT_FOUND));
        var newUserOrderEntity= userOrderService.cooking(userOrderEntity);
        var response = userOrderConverter.toResponse(newUserOrderEntity);
        return UserOrderDetailResponse.builder()
                .userOrderResponse(response)
                .storeMenuResponseList(userOrderRequest.getStoreMenuResponseList())
                .build();
    }

    public UserOrderDetailResponse delivery(UserOrderRequest userOrderRequest){
        var userOrderEntity = userOrderService.getUserOrder(userOrderRequest.getUserOrderResponse().getId())
                .orElseThrow(()->new ApiException(StoreErrorCode.USER_ORDER_NOT_FOUND));
        var newUserOrderEntity= userOrderService.delivery(userOrderEntity);
        var response = userOrderConverter.toResponse(newUserOrderEntity);
        return UserOrderDetailResponse.builder()
                .userOrderResponse(response)
                .storeMenuResponseList(userOrderRequest.getStoreMenuResponseList())
                .build();
    }

    public UserOrderDetailResponse receive(UserOrderRequest userOrderRequest){
        var userOrderEntity = userOrderService.getUserOrder(userOrderRequest.getUserOrderResponse().getId())
                .orElseThrow(()->new ApiException(StoreErrorCode.USER_ORDER_NOT_FOUND));
        var newUserOrderEntity= userOrderService.receive(userOrderEntity);
        var response = userOrderConverter.toResponse(newUserOrderEntity);
        return UserOrderDetailResponse.builder()
                .userOrderResponse(response)
                .storeMenuResponseList(userOrderRequest.getStoreMenuResponseList())
                .build();
    }
}
