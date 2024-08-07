package org.delivery.takeout.domain.kakao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetaDto {

    //검색어에 검색된 문서 수
    @JsonProperty("total_count")
    private Integer totalCount;
}
