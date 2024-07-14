package org.delivery.api.domain.review.service;

import org.delivery.api.domain.review.controller.model.ReviewEntityDtoRecord;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.review.ReviewRepository;
import org.delivery.db.review.enums.ReviewStatus;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.enums.UserStatus;
import org.delivery.db.userorder.UserOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.hibernate.criterion.Projections.id;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("서비스 로직 - 리뷰")
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    //test 대상
    @InjectMocks private ReviewService reviewService;

    @Mock private ReviewRepository reviewRepository;
    @Mock private UserOrderRepository userOrderRepository;

    @DisplayName("주문 ID로 조회 시, 해당하는 리뷰를 반환")
    @Test
    void givenUserOrderId_whenSearchReviews_thenReturnReviews(){
        //given
        Long userOrderId = 1L;

        //when
        ReviewEntityDtoRecord reviewEntityDto = reviewService.userOrderReview(userOrderId);

        //then
        assertThat(reviewEntityDto).isNotNull();
        //then(userOrderRepository).should().findById(userOrderId);
    }

    @DisplayName("유저 ID로 조회 시, 유저의 리뷰를 반환(가게/별점/내용/메뉴/작성 날짜/사장님)")
    @Test
    void givenUserId_whenGetUserId_thenReturnReviews(){
        //given
        Pageable pageable = Pageable.ofSize(20);
        Long userId = 1L;
        given(reviewRepository.findByUserId(userId,pageable)).willReturn(Page.empty());

        //when
        Page<ReviewEntity> reviewEntityPage = reviewService.getUserReview(userId,pageable);

        //then
        assertThat(reviewEntityPage).isEmpty();
        then(reviewRepository).should().findByUserId(userId,pageable);
    }

    //todo 이거 store-domain으로 옮겨야 함
    @DisplayName("스토어 ID로 조회 시, 해당 가게의 리뷰를 반환(작성자/별점/내용/메뉴/응답)")
    @Test
    void givenStoreId_whenGetStoreId_thenReturnReviews(){
        //given
        Pageable pageable = Pageable.ofSize(20);
        Long storeId = 1L;
        given(reviewRepository.findByStoreId(storeId,pageable)).willReturn(Page.empty());
        //when
        Page<ReviewEntity> reviewEntityPage = reviewService.getStoreReview(storeId,pageable);
        //then
        assertThat(reviewEntityPage).isEmpty();
        then(reviewRepository).should().findByStoreId(storeId,pageable);
    }

    @DisplayName("리뷰 입력 시, 리뷰를 생성한다")
    @Test
    void givenReviewText_whenSavingReview_thenSaveReview(){
        //given
        ReviewEntity review = createReviewEntity();
        given(reviewRepository.save(any(ReviewEntity.class))).willReturn(createReviewEntity());

        //when
        reviewService.saveReview(review);

        //then
        then(reviewRepository).should().save(any(ReviewEntity.class));
    }

    @DisplayName("리뷰의 수정 정보를 입력 시, 수정된 리뷰를 업데이트한다")
    @Test
    void givenUpdatedReview_whenUpdatingReview_thenUpdateReview(){
        //given
        var orgReview = createReviewEntity(1l);
        var updateReview = createReviewEntity(4,"hello");
        given(reviewRepository.getReferenceById(updateReview.getId())).willReturn(orgReview);

        //when
        reviewService.updateReview(updateReview);

        //then
        assertThat(orgReview)
                .hasFieldOrPropertyWithValue("reviewText", updateReview.getReviewText())
                .hasFieldOrPropertyWithValue("star", updateReview.getStar());
        then(reviewRepository).should().getReferenceById(updateReview.getId());


    }

    @DisplayName("리뷰 ID 입력 시, 리뷰 삭제")
    @Test
    void givenReviewId_whenDeletingReview_thenDeleteReview(){
        //given
        Long reviewId = 1L;
        willDoNothing().given(reviewRepository).deleteById(reviewId);
        //when
        reviewService.deleteReview(reviewId);
        //then
        then(reviewRepository).should().deleteById(reviewId);
    }

    private ReviewEntity createReviewEntity(double star, String reviewText){
        return ReviewEntity.builder()
                .id(1L)
                .user(createUserEntity())
                .store(createStoreEntity())
                .star(star)
                .status(ReviewStatus.REGISTERED)
                .reviewCreatedAt(LocalDateTime.now())
                .reviewText(reviewText)
                .reviewUpdatedAt(LocalDateTime.now())
                .replyCreatedAt(LocalDateTime.now())
                .replyText("hi")
                .replyUpdatedAt(LocalDateTime.now())
                .build();
    }

    private ReviewEntity createReviewEntity(Long id){
        var review = ReviewEntity.builder()
                .user(createUserEntity())
                .store(createStoreEntity())
                .star(5)
                .status(ReviewStatus.REGISTERED)
                .reviewCreatedAt(LocalDateTime.now())
                .reviewText("hi")
                .reviewUpdatedAt(LocalDateTime.now())
                .replyCreatedAt(LocalDateTime.now())
                .replyText("hi")
                .replyUpdatedAt(LocalDateTime.now())
                .build();
        ReflectionTestUtils.setField(review, "id", id);
        return review;
    }

    private ReviewEntity createReviewEntity(){
        return ReviewEntity.builder()
                .id(1L)
                .user(createUserEntity())
                .store(createStoreEntity())
                .star(5)
                .status(ReviewStatus.REGISTERED)
                .reviewCreatedAt(LocalDateTime.now())
                .reviewText("hi")
                .reviewUpdatedAt(LocalDateTime.now())
                .replyCreatedAt(LocalDateTime.now())
                .replyText("hi")
                .replyUpdatedAt(LocalDateTime.now())
                .build();

    }

    private UserEntity createUserEntity(){
        return UserEntity.builder()
                .name("julee")
                .email("wnfl@")
                .password("0000")
                .address("서울")
                .registeredAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .unregisteredAt(LocalDateTime.now())
                .status(UserStatus.REGISTERED)
                .build();
    }

    private StoreEntity createStoreEntity(){
        return StoreEntity.builder()
                .name("에그몬")
                .address("주소")
                .phoneNumber("010")
                .category(StoreCategory.CAFE)
                .minimumAmount(new BigDecimal(10))
                .minimumDeliveryAmount(new BigDecimal(10))
                .star(5)
                .status(StoreStatus.REGISTERED)
                .thumbnailUrl("image")
                .build();
    }


}