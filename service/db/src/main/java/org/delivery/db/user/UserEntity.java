package org.delivery.db.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.delivery.db.BaseEntity;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.user.enums.UserStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity//(name="user") 아래와 비슷
@Table(name="user")
@Data
@NoArgsConstructor//BaseEntity에 생성자 안넣어주면 에러남
@AllArgsConstructor// //
@SuperBuilder//부모의 entity(BaseEntity)도 빌더에 포함
@EqualsAndHashCode(callSuper = true)//callSuper=true: 부모의 entity를 포함해서 비교, false면 해당 entity에서 비교
public class UserEntity extends BaseEntity {

    @Column(length = 50,nullable = false)//db의 user 테이블과 속성 일치시킴
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;//enum 사용해서 자료형 String -> UserStatus

    @Column(length = 150, nullable = false)
    private String address;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    @OrderBy("reviewCreatedAt")
    private Set<ReviewEntity> reviewEntitySet = new HashSet<>();
    //private List<ReviewEntity> reviewEntityList;
}
