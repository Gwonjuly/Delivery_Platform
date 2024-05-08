package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    //특정 유저의 모든 주문 내역 가져오기
    //특정 유저: user_order의 user_id
    //select * from user_order where user_id = ? and status =? order by id desc limit 1
    List<UserOrderEntity> findAllByUserIDAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    //특정 유저의 특정 주문 내역 가져오기
    //특정 유저: user_order의 user_id, 특정 주문: user_order의 id
    Optional<UserOrderEntity> findAllFirstByIdAndStatusAndUserID(Long id, UserOrderStatus status, Long userId);

    //select * from user_order where user_id=? and status in (?,?..) order by id desc, in: 복수 개로 select
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);
}
