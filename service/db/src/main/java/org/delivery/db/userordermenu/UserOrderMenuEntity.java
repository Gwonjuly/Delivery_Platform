package org.delivery.db.userordermenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="user_order_menu")
public class UserOrderMenuEntity extends BaseEntity {

    @JoinColumn (nullable = false, name = "user_order_id")
    @ManyToOne
    private UserOrderEntity userOrder;// 1(user_order) : N(user_order_menu)

    @JoinColumn(nullable = false) //name ="store_menu_id"
    @ManyToOne
    private StoreMenuEntity storeMenu;// N(user_order_menu) : 1(store_menu_id)

    @Enumerated(EnumType.STRING)
    @Column(length = 50,nullable = false)
    private UserOrderMenuStatus status;

}
