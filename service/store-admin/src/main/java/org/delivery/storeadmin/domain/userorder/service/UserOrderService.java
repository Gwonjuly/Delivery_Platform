package org.delivery.storeadmin.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    //주문이 들어왔을 때, 해당 정보 반환
    public Optional<UserOrderEntity> getUserOrder(Long userOrderId){
        return  userOrderRepository.findById(userOrderId);
    }

    //주문 상태 변경 method:status
    public UserOrderEntity setStatus(UserOrderEntity userOrderEntity, UserOrderStatus userOrderStatus){
        userOrderEntity.setStatus(userOrderStatus);//entity 검색 후, save: insert(신규 추가)가 아닌 update
        return userOrderRepository.save(userOrderEntity);
    }

    //가게 주문 확인: accepted_at
    public UserOrderEntity accept(UserOrderEntity userOrderEntity){
        userOrderEntity.setAcceptedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.ACCEPT);
    }

    //가게 조리 시작
    public UserOrderEntity cooking(UserOrderEntity userOrderEntity){
        userOrderEntity.setCookingStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.COOKING);
    }

    //라이더 배달 시작
    public UserOrderEntity delivery(UserOrderEntity userOrderEntity){
        userOrderEntity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.DELIVERY);
    }

    //배달 완료
    public UserOrderEntity receive(UserOrderEntity userOrderEntity){
        userOrderEntity.setReceivedAt(LocalDateTime.now());
        return setStatus(userOrderEntity,UserOrderStatus.RECEIVE);
    }
}
