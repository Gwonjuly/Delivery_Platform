package org.delivery.storeadmin.presentation;

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
@WebMvcTest(PageController.class)
class PageControllerTest {

    private final MockMvc mvc;

    public PageControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 루트 페이지 -> 가맹점 관리 페이지")
    @Test
    void givenNothing_whenRequestReplyView_then()throws Exception{
        //given

        //when & then
        mvc.perform(MockMvcRequestBuilders.get(("/")))
                .andExpect(status().isOk())
                .andExpect(view().name(""))
                .andExpect(content().contentTypeCompatibleWith((MediaType.TEXT_HTML)))
                .andExpect(view().name("reply/view"));
    }

}