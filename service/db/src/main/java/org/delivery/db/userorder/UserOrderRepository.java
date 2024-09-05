package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long>
{

    //select * from user_order where user_id = ? and status =? order by id desc limit 1
    List<UserOrderEntity> findAllByUserIdOrderByIdDesc(Long userId);

    //특정 유저의 특정 주문 내역 가져오기
    Optional<UserOrderEntity> findAllFirstByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);

    //select * from user_order where user_id=? and status in (?,?..) order by id desc, in: 복수 개로 select
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);


    //select *from user_order where id =? and userId=?
    Optional<UserOrderEntity> findAllByIdAndUserId(Long id, Long userId);

    Page<UserOrderEntity> findByUserId(Long userId, Pageable pageable); //유저의 모든 주문 내역
    //Page<UserOrderEntity> findByUserIdAnAndStatus(Long userId, UserOrderStatus status, Pageable pageable); // 유저의 주문 상태
}
