package org.delivery.db.userordermenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="user_order_menu")
public class UserOrderMenuEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userOrderId;// 1(user_order) : N(user_order_id)

    @Column(nullable = false)
    private Long storeMenuId;// 1(store_menu) : N(store_menu_id)

    @Enumerated(EnumType.STRING)
    @Column(length = 50,nullable = false)
    private UserOrderMenuStatus status;
}
