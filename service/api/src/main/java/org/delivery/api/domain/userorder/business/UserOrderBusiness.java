package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;//store_menu call
    private final UserOrderConverter userOrderConverter;

    /**
     * 1. 로그인한 사용자, 메뉴 id 리스트 받기
     * 2. userOrder 생성
     * 3. userOrderMenu 생성: userOrder와 store_menu_id 맵핑
     * 4. 응답 생성
     *
     */

    public void userOrder(User user, UserOrderRequest userOrderRequest) {
        var storeMenuEntityList=userOrderRequest.getStoreMenuIdList()
                .stream()
                .map(menuId -> storeMenuService.getStoreMenuWithThrow(menuId))
                .collect(Collectors.toList());
        var userOrderEntity=userOrderConverter.toEntity(user, storeMenuEntityList);

        //주문: entity=user 정보 및 총 금액
        var newUserOrderEntity=userOrderService.order(userOrderEntity);

        //mapping with user_order_menu
    }
}
