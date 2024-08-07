package org.delivery.api.domain.user.service;

/*import org.delivery.api.domain.testmodel.TestEntity;
import org.delivery.api.domain.testmodel.UserEntityTest;*/
import org.delivery.common.error.UserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;

    @Test
    void 회원가입시_동일이메일로_회원가입하는_경우_에러반환(){//정상 동작
        var userEntity = mock(UserEntity.class);
        var testEntity = mock(UserEntity.class);

        when(userRepository.findFirstByEmail(testEntity.getEmail()))
                .thenReturn(Optional.of(testEntity));

        ApiException e =Assertions.assertThrows(ApiException.class, ()->userService.register(testEntity));
        Assertions.assertEquals(UserErrorCode.USER_NAME_DUPLICATED, e.getErrorCodeIfs());
    }

    @DisplayName("user 등록 테스트")
    @Test
    void register() {
        long a = userRepository.count();
        UserEntity user = UserEntity.builder()
                .name("julee")
                .registeredAt(LocalDateTime.now())
                .email("df")
                .address("sdf")
                .password("12")
                .status(UserStatus.REGISTERED)
                .lastLoginAt(LocalDateTime.now())
                .unregisteredAt(LocalDateTime.now())
                .build();

        var user2 = userRepository.save(user);

        assertEquals(user.getName(), user2.getName());

    }

}