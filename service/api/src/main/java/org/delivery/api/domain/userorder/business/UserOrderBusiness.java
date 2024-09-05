package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Business;
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
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Transactional
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

    public UserOrderResponse userOrder(User user, UserOrderRequest userOrderRequest) {

        var storeEntity = storeService.getStoreWithThrow(userOrderRequest.getStoreId());

        var storeMenuEntityList=userOrderRequest.getStoreMenuIdList()
                .stream()
                .map(menuId -> storeMenuService.getStoreMenuWithThrow(menuId))
                .collect(Collectors.toList());

        var userOrderEntity=userOrderConverter.toEntity(user, storeEntity ,storeMenuEntityList);

        var newUserOrderEntity=userOrderService.order(userOrderEntity);

        var userOrderMenuEntityList=storeMenuEntityList.stream()
                .map(it->{
                    return userOrderMenuConverter.toEntity(newUserOrderEntity,it);
                })
                .collect(Collectors.toList());

        userOrderMenuEntityList.forEach(it->{
            userOrderMenuService.order(it);
        });

        userOrderProducer.sendOrder(newUserOrderEntity);

        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    @Transactional(readOnly = true)
    public List<UserOrderDetailResponse> viewAll(User user){
        var userOrderEntityList = userOrderService.getUserOrderList(user.getId());

        var userOrderDetailResponseList=userOrderEntityList.stream().map(userOrderEntity->{

            var userOrderMenuList=userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                    .collect(Collectors.toList());

            var storeMenuEntityList=userOrderMenuList.stream()
                    .map(it -> it.getStoreMenu())
                    .collect(Collectors.toList());

            var storeEntity=userOrderEntity.getStore();

            return UserOrderDetailResponse.builder()
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).collect(Collectors.toList());
        return userOrderDetailResponseList;
    }

    @Transactional(readOnly = true)
    public List<UserOrderDetailResponse> current(User user) {
        var userOrderEntityList=userOrderService.current(user.getId());

       var userOrderDetailResponseList=userOrderEntityList.stream().map(userOrderEntity->{

           var userOrderMenuEntityList = userOrderEntity.getUserOrderMenuList().stream()
                   .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                   .collect(Collectors.toList());

            var storeMenuEntityList=userOrderMenuEntityList.stream()
                    .map(i->{
                        return i.getStoreMenu();
                    })
                    .collect(Collectors.toList());

            var storeEntity = userOrderEntity.getStore();

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).collect(Collectors.toList());
       return userOrderDetailResponseList;
    }

    @Transactional(readOnly = true)
    public List<UserOrderDetailResponse> history(User user) {
        var userOrderEntityList=userOrderService.history(user.getId());

        var userOrderDetailResponseList=userOrderEntityList.stream().map(userOrderEntity->{

            var userOrderMenuList=userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                    .collect(Collectors.toList());

            var storeMenuEntityList=userOrderMenuList.stream()
                    .map(it -> it.getStoreMenu())
                    .collect(Collectors.toList());

            var storeEntity=userOrderEntity.getStore();

            return UserOrderDetailResponse.builder()
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).collect(Collectors.toList());
        return userOrderDetailResponseList;
    }

    @Transactional(readOnly = true)
    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity=userOrderService.getUserOrderWithTOutStatusWithThrow(orderId,user.getId());

        var userOrderMenuList=userOrderEntity.getUserOrderMenuList().stream()
                .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                .collect(Collectors.toList());

        var storeMenuEntityList=userOrderMenuList.stream()
                .map(it -> it.getStoreMenu())
                .collect(Collectors.toList());

        var storeEntity=userOrderEntity.getStore();

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }

    public Page<UserOrderResponse> alarmList(User user, Pageable pageable) {
        var userOrderEntityList = userOrderService.getUserOrderList(user.getId());
        var pageUserOrder= userOrderEntityList.stream()
                .map(it->userOrderConverter.toResponse(it))
                .collect(Collectors.toUnmodifiableList());
        return new PageImpl<>(pageUserOrder);
    }
}
