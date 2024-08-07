package org.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreCategory {

    CHINESE_FOOD("중식","중식"),
    WESTERN_FOOD("양식","양식"),
    KOREAN_FOOD("한식","한식"),
    JAPANESE_FOOD("일식","일식"),
    CHICKEN("치킨","치킨"),
    PIZZA("피자","피자"),
    HAMBURGER("햄버거","햄버거"),
    CAFE("카페","카페"),
    BAR("감성주점","감성주점"),
    CONVENIENT("편의점","편의점"),
    PORK_FEET("족발/보쌈","족발/보쌈"),
    MIDNIGHT_SNACK("야식","야식"),
    MEAT("고기/구이","고기/구이"),
    SNACK("분식","분식"),
    ETC("기타","기타"),
    JJIGAE("찜/탕/찌개","찜/탕/찌개"),
    SASHIMI("회","회"),
    BREAD("빵","빵"),
    ASIAN_FOOD("아시안 음식","아시안 음식"),
    ;

    private String display;
    private String description;
}
