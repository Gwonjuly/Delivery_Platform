package org.delivery.api.domain.userordermenu.converter;

import org.delivery.common.annotation.Converter;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

@Converter
public class UserOrderMenuConverter {

    /** user_order_menu Mapping
     * UserOrderEntity의 id + StoreMenuEntity-> UserOrderMenuEntity
     */
    public UserOrderMenuEntity toEntity(UserOrderEntity userOrderEntity, StoreMenuEntity storeMenuEntity){
        return UserOrderMenuEntity.builder()
                .userOrder(userOrderEntity)
                .storeMenu(storeMenuEntity)
                .build();
    }
}
