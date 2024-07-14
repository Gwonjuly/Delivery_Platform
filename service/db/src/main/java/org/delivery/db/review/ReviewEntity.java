package org.delivery.db.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.asm.Advice;
import org.delivery.db.BaseEntity;
import org.delivery.db.review.enums.ReviewStatus;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.user.UserEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.hibernate.event.spi.LoadEventListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "review")
public class ReviewEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn (nullable = false, name="user_id")
    private UserEntity user; // -> userEntity

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_order_id")
    private UserOrderEntity userOrder; // ->userOrderEntity

    @ManyToOne
    @JoinColumn(nullable = false, name = "store_id")
    private StoreEntity store; // -> storeEntity

    @Column(nullable = false)
    private double star; //별점

    @Column(nullable = false)
    private String reviewText; //리뷰 내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @Column(nullable = false)
    private LocalDateTime reviewCreatedAt; //리뷰 생성 일시

    private LocalDateTime reviewUpdatedAt; //리뷰 수정 일시

    private String replyText; //사장님 댓글 내용

    private LocalDateTime replyCreatedAt; //사장님 댓글 생성 일시

    private LocalDateTime replyUpdatedAt; //사장님 댓글 수정 일시

    /*public static ReviewEntity of(UserOrderEntity userOrderEntity, UserEntity userEntity, StoreEntity storeEntity, String reviewText) {
        return new ReviewEntity(userOrderEntity, userEntity, storeEntity,reviewText);
    }*/
}
