package org.delivery.db.user;

import org.delivery.db.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {//<entity, id=primary key>, id라는 어노테이션 붙은 변수의 타입

    /* TODO LIST 유저 찾기
    select * from user where id =? and status = ? order by id desc limit 1
    findFirstBy_Id_And_Status_OrderBy_Id_Desc =user 테이블로부터 id와 status를 찾고 id를 최신 것부터 정렬
    OrderByIdDesc: 최신 것부터 정렬
     */
    Optional<UserEntity> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus status);

    /* TODO LIST 로그인 시, id와 패스워드
    select * from user where email =? and password =? and status =? order by id desc limit 1
     */
    Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus status);

    List<UserEntity> findAllByStatusOrderByIdDesc(UserStatus status);
}
