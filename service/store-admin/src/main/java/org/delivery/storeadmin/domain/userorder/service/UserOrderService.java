package org.delivery.storeadmin.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    //주문이 들어왔을 때, 해당 정보 반환
    public Optional<UserOrderEntity> getUserOrder(Long userOrderId){
        return  userOrderRepository.findById(userOrderId);
    }
}
