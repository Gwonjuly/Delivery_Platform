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
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public UserOrderEntity getUserOrderWithTOutStatusWithThrow(Long id, Long userId){
        return userOrderRepository.findAllByIdAndUserId(id,userId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }
    public Optional<UserOrderEntity> getUserOrder(Long userOrderId){
        return userOrderRepository.findById(userOrderId);
    }

    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId){
        return userOrderRepository.findAllFirstByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED,userId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public List<UserOrderEntity> getUserOrderList(Long userId){
        return userOrderRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId,statusList);
    }

    public UserOrderEntity order(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it->{
                    userOrderEntity.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public List<UserOrderEntity> current(Long userId){
        return getUserOrderList(userId,
                List.of(
                        UserOrderStatus.ORDER,
                        UserOrderStatus.COOKING,
                        UserOrderStatus.DELIVERY,
                        UserOrderStatus.ACCEPT
                ));
    }

    public List<UserOrderEntity> history(Long userId){
        return getUserOrderList(userId,
                List.of(
                        UserOrderStatus.RECEIVE
                ));
    }
}
