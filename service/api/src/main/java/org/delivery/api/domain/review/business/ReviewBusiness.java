package org.delivery.api.domain.review.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.review.controller.model.ReviewDetailResponse;
import org.delivery.api.domain.review.controller.model.ReviewRegisterRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.review.controller.model.ReviewUpdateRequest;
import org.delivery.api.domain.review.converter.ReviewConverter;
import org.delivery.api.domain.review.service.ReviewService;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.common.annotation.Business;
import org.delivery.db.review.ReviewRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewBusiness {

    private final ReviewService reviewService;
    private final StoreConverter storeConverter;
    private final StoreMenuConverter storeMenuConverter;
    private final ReviewConverter reviewConverter;
    private final UserOrderService userOrderService;
    private final StoreService storeService;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

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
    }

    public ReviewDetailResponse formReview(User user, Long userOrderId) {

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
    }

    @Transactional
    public ReviewResponse saveReview(User user, ReviewRegisterRequest reviewRegisterRequest){
        var storeEntity = storeService.searchByStoreName(reviewRegisterRequest.getStoreName());
        var userOrderEntity = userOrderService.getUserOrder(reviewRegisterRequest.getUserOrderId());
        var userEntity = userService.getUserWithThrow(user.getId());
        var reviewEntity = reviewConverter.toEntity(reviewRegisterRequest, userEntity,userOrderEntity.get(),storeEntity.get());
        var newReviewEntity = reviewService.saveReview(reviewEntity);
        return reviewConverter.toResponse(newReviewEntity);
    }

    @Transactional
    public ReviewResponse updateReview(User user, ReviewUpdateRequest reviewUpdateRequest) {
        var reviewEntity = reviewService.getReview(reviewUpdateRequest.getReviewId());
        var newReviewEntity = Optional.ofNullable(reviewEntity)
                .map(it-> {
                    it.setStar(reviewUpdateRequest.getStar());
                    it.setReviewText(reviewUpdateRequest.getReviewText());
                    it.setReviewUpdatedAt(LocalDateTime.now());
                    reviewService.updateReview(it);
                    return it;
                })
                .orElseThrow(EntityNotFoundException::new);
        return reviewConverter.toResponse(newReviewEntity);
    }

    @Transactional
    public void deleteReview(User user, Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    public Page<ReviewDetailResponse> viewStoreReview(Long storeId,Pageable pageable) {
        var storeReviewEntityPage = reviewService.getStoreReview(storeId,pageable);
        var storeEntity = storeService.getStoreWithThrow(storeId);

        var reviewResponsePage = storeReviewEntityPage.stream().map(reviewEntity -> {
            var userOrderEntity = reviewEntity.getUserOrder();
            var userOrderMenuList = userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it->it.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                    .collect(Collectors.toList());
            var storeMenuEntityList = userOrderMenuList.stream()
                    .map(it->it.getStoreMenu())
                    .collect(Collectors.toList());

            return ReviewDetailResponse.builder()
                    .reviewResponse(reviewConverter.toResponse(reviewEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).collect(Collectors.toList());

        return new PageImpl<>(reviewResponsePage);
    }
}
