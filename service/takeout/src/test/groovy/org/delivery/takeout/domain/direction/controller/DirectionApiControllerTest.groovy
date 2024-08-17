package org.delivery.takeout.domain.direction.controller

import org.delivery.takeout.domain.direction.controller.model.DirectionResponse
import org.delivery.takeout.domain.store.service.StoreTakeoutService
import org.mockito.Mock
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class DirectionApiControllerTest extends Specification {

    private MockMvc mvc
    private StoreTakeoutService storeTakeoutService = Mock()
    private List<DirectionResponse> directionResponseList

    def setup(){
        mvc = MockMvcBuilders.standaloneSetup(new DirectionApiController(storeTakeoutService)).build()

        directionResponseList = new ArrayList<>()
        directionResponseList.addAll(
                DirectionResponse.builder()
                        .storeName("가게1")
                        .build(),
                DirectionResponse.builder()
                        .storeName("가게2")
                        .build()
        )
    }

    def "main - GET/"(){
        expect:
        mvc.perform(get("/api/take-out/"))
            .andExpect(handler().handlerType(DirectionApiController.class))
            .andExpect(handler().methodName("main"))
            .andExpect(status().isOk())
            .andExpect(view().name("main"))
            .andDo(log())
    }

    def "search - POST/"(){
        given:
        String inputAddress = "강원도 원주시 금불 1길 52"

        when:
        def resultActions = mvc.perform(post("/api/take-out/search")
            .param("address",inputAddress))

        then:
        1 * storeTakeoutService.storeTakeoutList(argument->{
            assert argument == inputAddress
        }) >> directionResponseList

        resultActions
            .andExpect(status().isOk())
            .andExpect(view().name("directionList"))
            .andExpect(model().attributeExists("directionList"))
            .andExpect(model().attribute("directionList",directionResponseList))
            .andDo(print())
    }

}
