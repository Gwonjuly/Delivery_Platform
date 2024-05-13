package org.delivery.db.storeuser;

import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    //이메일, 상태
    //select *from store_user where email =? and status =? order by id desc
    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus status);
}
