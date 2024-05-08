package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeIfs;
import org.delivery.api.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    //특정 유저의 특정 주문 가져오기
    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId){
        return userOrderRepository.findAllFirstByIdAndStatusAndUserID(id, UserOrderStatus.REGISTERED,userId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    //특정 유저의 모든 주문 가져오기
    public List<UserOrderEntity> getUserOrderList(Long userId){
        return userOrderRepository.findAllByUserIDAndStatusOrderByIdDesc(userId,UserOrderStatus.REGISTERED);
    }

    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId,statusList);
    }
    //주문 생성

    //가게 주문 확인

    //가게 조리 시자작

    //라이더 배달 시작

    //배달 완료
}
