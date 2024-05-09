package org.delivery.api.domain.userorder.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {

    public UserOrderEntity toEntity(User user, List<StoreMenuEntity> storeMenuEntityList){

        //메뉴의 총 금액
        var totalAmount=storeMenuEntityList.stream()
                .map(it->it.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);//초기 값, 리스트 순환 sum 함수

        return UserOrderEntity.builder()
                .userId(user.getId())
                .amount(totalAmount)
                .build();
    }
}
