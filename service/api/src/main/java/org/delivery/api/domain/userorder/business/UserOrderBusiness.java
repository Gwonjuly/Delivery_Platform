package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;//store_menu call
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

    /**
     * 1. 로그인한 사용자, 메뉴 id 리스트 받기
     * 2. userOrder 생성
     * 3. userOrderMenu 생성: userOrder와 store_menu_id 맵핑
     * 4. 응답 생성
     *
     */

    public UserOrderResponse userOrder(User user, UserOrderRequest userOrderRequest) {

        //메뉴 id 리스트
        var storeMenuEntityList=userOrderRequest.getStoreMenuIdList()
                .stream()
                .map(menuId -> storeMenuService.getStoreMenuWithThrow(menuId))
                .collect(Collectors.toList());

        //entity: use + menu id를 통한 주문 총 금액
        var userOrderEntity=userOrderConverter.toEntity(user, storeMenuEntityList);

        //주문 생성(REGISTERED): entity=user 정보 및 총 금액
        var newUserOrderEntity=userOrderService.order(userOrderEntity);

        //맵핑 with user_order_menu(user_order의 id(동일) + store_menu의 id(얘가 다름))
        var userOrderMenuEntityList=storeMenuEntityList.stream()
                .map(it->{
                    //menu id(단일) + user
                    return userOrderMenuConverter.toEntity(newUserOrderEntity,it);//userOrderEntity 넣어보기
                })
                .collect(Collectors.toList());
        /*
        userOrderMenuEntityList:
        [(user_order_id(1), menu_id(1)),
         (user_order_id(1), menu_id(2)),
         (user_order_id(1), menu_id(3))]
         */

        //맵핑된 user_order_menu를 db에 저장
        userOrderMenuEntityList.forEach(it->{
            userOrderMenuService.order(it);
        });

        //비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntity);

        //to response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        var userOrderEntityList=userOrderService.current(user.getId());

        //주문 한 건씩  처리
       var userOrderDetailResponseList=userOrderEntityList.stream().map(it->{

            //사용자가 주문한 메뉴
            var userOrderMenuEntityList=userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList=userOrderMenuEntityList.stream()
                    .map(i->{
                        var storeMenuEntity=storeMenuService.getStoreMenuWithThrow(i.getStoreMenuId());
                        //storeEntity=storeService.getStoreWithThrow(storeMenuEntity.getStoreId());
                        return storeMenuEntity;
                    })
                    .collect(Collectors.toList());

            //사용자가 주문한 스토어 TODO .get()의 null에 대해 리팩토링 필요
            var storeEntity=storeService.getStoreWithThrow(
                    storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).collect(Collectors.toList());
       return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        var usrOrderEntityList=userOrderService.history(user.getId());
        var userOrderDetailResponseList=usrOrderEntityList.stream().map(it->{
            var userOrderMenuList=userOrderMenuService.getUserOrderMenu(it.getId());

            var storeMenuEntityList=userOrderMenuList.stream()
                    .map(i->{
                        return storeMenuService.getStoreMenuWithThrow(i.getStoreMenuId());
                    })
                    .collect(Collectors.toList());

            var storeEntity=storeService.getStoreWithThrow(
                    storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).collect(Collectors.toList());
        return userOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity=userOrderService.getUserOrderWithTOutStatusWithThrow(orderId,user.getId());

        //사용자가 주문한 메뉴
        var userOrderMenuList=userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        var storeMenuEntityList=userOrderMenuList.stream()
                .map(it->{
                    return storeMenuService.getStoreMenuWithThrow(it.getStoreMenuId());
                }).collect(Collectors.toList());

        //사용자가 주문한 스토어
        var storeEntity=storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
