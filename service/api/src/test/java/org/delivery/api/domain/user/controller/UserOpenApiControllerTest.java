package org.delivery.api.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.UserErrorCode;
import org.delivery.common.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.matchers.text.ValuePrinter.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserOpenApiControllerTest {

    @Autowired private MockMvc mvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private UserBusiness userBusiness;


    @Test
    void 회원가입() throws Exception{
        //given
        String name = "julee";
        String email = "julee@example.com";
        String address = "원주시";
        String password = "123456";
        var request = new UserRegisterRequest(name, email, address, password);
        //when
        when(userBusiness.register(request)).thenReturn(mock(UserResponse.class));

        mvc.perform(post("/open-api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserRegisterRequest(name,email,address,password)))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입시_동일이메일로_회원가입하는_경우_에러반환() throws Exception{
        //given
        String name = "julee";
        String email = "julee@example.com";
        String address = "원주시";
        String password = "123456";
        var request = new UserRegisterRequest(name, email, address, password);

        //when
        when(userBusiness.register(request)).thenThrow(new ApiException(UserErrorCode.USER_NAME_DUPLICATED));
        mvc.perform(post("/open-api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserRegisterRequest(name,email,address,password)))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(UserErrorCode.USER_NAME_DUPLICATED.getErrorCode()));
    }

    @Test
    void 로그인() throws Exception{
        //given
        String email = "julee@example.com";
        String password = "123456";
        var request = new UserLoginRequest(email, password);
        //when
        when(userBusiness.login(request)).thenReturn(mock(TokenResponse.class));

        mvc.perform(post("/open-api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인시_유저정보가_없을_경우_에러반환() throws Exception{
        //given
        String email = "julee@example.com";
        String password = "123456";
        var request = new UserLoginRequest(email, password);

        //when
        when(userBusiness.login(request)).thenThrow(new ApiException(UserErrorCode.USER_NOT_FOUND));
        mvc.perform(post("/open-api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인시_잘못된_패스워드를_입력할_경우_에러반환() throws Exception{
        //given
        String email = "julee@example.com";
        String password = "123456";
        var request = new UserLoginRequest(email, password);

        //when
        when(userBusiness.login(request)).thenThrow(new ApiException(UserErrorCode.USER_PW_INCONSISTENCY));
        mvc.perform(post("/open-api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}