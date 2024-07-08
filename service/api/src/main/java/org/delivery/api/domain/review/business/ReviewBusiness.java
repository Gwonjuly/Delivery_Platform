package org.delivery.api.domain.review.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.review.controller.model.ReviewDetailResponse;
import org.delivery.api.domain.review.controller.model.ReviewRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.review.converter.ReviewConverter;
import org.delivery.api.domain.review.service.ReviewService;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.common.annotation.Business;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.review.enums.ReviewStatus;
import org.delivery.db.user.UserEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Transactional
public class ReviewBusiness {

    private final ReviewService reviewService;
    private final StoreConverter storeConverter;
    private final StoreMenuConverter storeMenuConverter;
    private final ReviewConverter reviewConverter;
    private final UserOrderService userOrderService;

    @Transactional(readOnly = true)
    public Page<ReviewDetailResponse> view(User user, Pageable pageable) {
        var userReviewEntityPage = reviewService.getUserReview(user.getId(), pageable);

        var reviewResponsePage = userReviewEntityPage.stream().map(reviewEntity -> {

            var userEntity = reviewEntity.getUser();
            var storeEntity = reviewEntity.getStore();
            var userOrderEntity = reviewEntity.getUserOrder();

            //사용자가 주문한 메뉴
            var usrOrderMenuList = userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                    .collect(Collectors.toList());
            //메뉴 리스트
            var storeMenuEntityList = usrOrderMenuList.stream()
                    .map(it->it.getStoreMenu())
                    .collect(Collectors.toList());

            return ReviewDetailResponse.builder()
                    .reviewResponse(reviewConverter.toResponse(reviewEntity))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }).collect(Collectors.toList());
        return new PageImpl<>(reviewResponsePage);
        //return (Page<ReviewDetailResponse>) reviewResponsePage;
    }

    /*@Transactional(readOnly = true)
    public ReviewDetailResponse formReview(User user, Long userOrderId) {
        //유저 오더 ID로 리뷰 작성 페이지 요청  받기
        //유저 오더 ID로 해당 리뷰의 등록 여부 확인
        // 등록 시, 리뷰 엔티티의 모든 내용 반환
        // 미 등록 시, 가게 이름과 주문 내용 반환
        //
        var reviewEntity = reviewService.getUserOrderReview(userOrderId);

        if(reviewEntity.isEmpty()) { // 생성
            var userOrderEntity = userOrderService.getUserOrder(userOrderId)
                    .orElseThrow(()-> new RuntimeException("사용자 주문 내역 없음"));
            var userOrderMenuList = userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it->it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                    .collect(Collectors.toList());
            var storeMenuEntityList = userOrderMenuList.stream()
                    .map(it->it.getStoreMenu())
                    .collect(Collectors.toList());
            var storeEntity = userOrderEntity.getStore();
            return ReviewDetailResponse.builder()
                    .reviewResponse(null)
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .build();
        }
        else { // 수정
            var storeEntity = reviewEntity.get().getStore();
            var userOrderEntity = reviewEntity.get().getUserOrder();
            var userOrderMenuList = userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it->it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                    .collect(Collectors.toList());
            var storeMenuEntityList = userOrderMenuList.stream()
                    .map(it->it.getStoreMenu())
                    .collect(Collectors.toList());

                return ReviewDetailResponse.builder()
                        .reviewResponse(reviewConverter.toResponse(reviewEntity.get()))
                        .storeResponse(storeConverter.toResponse(storeEntity))
                        .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                        .build();
        }
    }*/

    //void
   /* public ReviewResponse saveReview(User user, ReviewRequest reviewRequest){
        //
        var reviewEntity = reviewConverter.toEntity(reviewRequest);
    }*/
}
