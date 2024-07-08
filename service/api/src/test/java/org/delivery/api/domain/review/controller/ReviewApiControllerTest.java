package org.delivery.api.domain.review.controller;

import org.delivery.api.domain.review.business.ReviewBusiness;
import org.delivery.api.domain.review.controller.model.ReviewDetailResponse;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.user.enums.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("review 컨트롤러 - 리뷰")

@WebMvcTest(ReviewApiController.class)
class ReviewApiControllerTest {

    private final MockMvc mvc;

    @MockBean private ReviewBusiness reviewBusiness;
    public ReviewApiControllerTest(@Autowired MockMvc mvc) {
        this.mvc= mvc;
    }


    @DisplayName("[view][GET]: 유저의 리뷰 리스트 페이지 - 정상 호출")
    @Test
    public void givenUserId_whenRequestReviewView_thenReturnUserReview() throws Exception {
        //given
        Long userId = 1L;
        User user = createUser();
        Pageable pageable = Pageable.ofSize(20);
        given(reviewBusiness.view(user,pageable)).willReturn(Page.empty());
        //when
        mvc.perform(get("/api/review/"))
                .andExpect((status().isOk()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("review/view"));
    }

    private ReviewDetailResponse createReviewResponse(){
        return ReviewDetailResponse.builder()
                .reviewResponse(null)
                .storeResponse(null)
                .storeMenuResponseList(null)
                .build();
    }

    private List<StoreMenuResponse> createStoreMenuResponse(){
        List<StoreMenuResponse> storeMenuResponseList = List.of(null);
        return storeMenuResponseList;
    }
    private StoreResponse createStoreResponse(){
        return null;
    }
    private User createUser(){
        return User.builder()
                .id(1L)
                .name("julee")
                .email("@@")
                .password("0000")
                .status(UserStatus.REGISTERED)
                .address("서울")
                .lastLoginAt(null)
                .registeredAt(null)
                .unregisteredAt(null)
                .build();
    }
}