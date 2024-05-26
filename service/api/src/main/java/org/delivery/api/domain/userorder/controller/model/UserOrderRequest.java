package org.delivery.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderRequest {

    /** 주문
     *  특정 사용자가 특정 메뉴를 주문
     *  특정 사용자: 로그인된 세션에 들어있는 사용자(로그인 된 사용자를 사용해서 별도의 요청으로 받을 필요 없음
     *  주문할 때 필요한 것: 특정 메뉴 id들
     */

    @NotNull
    private Long storeId;

    @NotNull
    private List<Long> storeMenuIdList;//user_order_mene 테이블에 있는 N의 id;

}
