package org.delivery.db.userorder.enums;

//@AllArgsConstructor
public enum UserOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    //@AllArgsConstructor 대신 생성자 만듦
    UserOrderStatus(String description){
        this.description=description;
    }
    private String description;
}
