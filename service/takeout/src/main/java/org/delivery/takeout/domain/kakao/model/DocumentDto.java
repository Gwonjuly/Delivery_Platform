package org.delivery.takeout.domain.kakao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDto {
    //https://developers.kakao.com/docs/latest/ko/local/dev-guide

    //전체 지번 주소 또는 전체 도로명 주소, 입력에 따라 결정됨
    @JsonProperty("address_name")//road_
    private String addressName;

    //Y 좌표값, 경위도인 경우 위도(latitude)
    @JsonProperty("y")
    private double latitude;

    //X 좌표값, 경위도인 경우 경도(longitude)
    @JsonProperty("x")
    private double longitude;

    @JsonProperty("distance")
    private double distance;

    @JsonProperty("place_name")
    private String storeName;
}
