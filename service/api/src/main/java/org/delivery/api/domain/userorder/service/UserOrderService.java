package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    @Transactional(readOnly = true)
    public UserOrderEntity getUserOrderWithTOutStatusWithThrow(Long id, Long userId){
        return userOrderRepository.findAllByIdAndUserId(id,userId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }
    public Optional<UserOrderEntity> getUserOrder(Long userOrderId){
        return userOrderRepository.findById(userOrderId);
    }

    //특정 유저의 특정 주문 가져오기
    @Transactional(readOnly = true)
    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId){
        return userOrderRepository.findAllFirstByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED,userId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    //특정 유저의 모든 주문 가져오기
    @Transactional(readOnly = true)
    public List<UserOrderEntity> getUserOrderList(Long userId){
        return userOrderRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId,statusList);
    }
    //주문 생성(create)
    public UserOrderEntity order(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it->{
                    userOrderEntity.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }
    //지금 현재 진행 중인 나의 주문 내역
    @Transactional(readOnly = true)
    public List<UserOrderEntity> current(Long userId){
        return getUserOrderList(userId,
                List.of(
                        UserOrderStatus.ORDER,
                        UserOrderStatus.COOKING,
                        UserOrderStatus.DELIVERY,
                        UserOrderStatus.ACCEPT
                ));
    }

    //과거에 주문했던 내역
    @Transactional(readOnly = true)
    public List<UserOrderEntity> history(Long userId){
        return getUserOrderList(userId,
                List.of(
                        UserOrderStatus.RECEIVE
                ));
    }
/*
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
    }*/
}
