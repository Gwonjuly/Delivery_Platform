package org.delivery.storeadmin.domain.reply.controller;

import org.delivery.storeadmin.config.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(ReplyApiController.class)
class ReplyApiControllerTest {

    private final MockMvc mvc;

    public ReplyApiControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 리뷰 관리 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestReplyView_then()throws Exception{
        //given

        //when & then
        mvc.perform(MockMvcRequestBuilders.get(("/api/reply/view")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith((MediaType.TEXT_HTML)))
                .andExpect(view().name("reply/view"));
    }

    @Test
    void saveReply() {
    }

    @Test
    void deleteReply() {
    }
}