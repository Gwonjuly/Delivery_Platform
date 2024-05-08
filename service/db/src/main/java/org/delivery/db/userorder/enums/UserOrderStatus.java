package org.delivery.db.userorder.enums;

//@AllArgsConstructor
public enum UserOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ORDER("주문"),
    ACCEPT("접수"),
    COOKING("조리 중"),
    DELIVERY("배달 중"),
    RECEIVE("배달 완료"),
    ;

    //@AllArgsConstructor 대신 생성자 만듦
    UserOrderStatus(String description){
        this.description=description;
    }
    private String description;
}
