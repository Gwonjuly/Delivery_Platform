package org.delivery.api.domain.review.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDetailResponse {
    //가게, 별점, 작성 날짜, 리뷰 내용, 메뉴 정보, 사장님 댓글/날짜
    private ReviewResponse reviewResponse;
    private List<StoreMenuResponse> storeMenuResponseList;
    private StoreResponse storeResponse; //가게 정보
}
