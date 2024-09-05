package org.delivery.takeout.domain.direction.controller

import org.delivery.takeout.domain.direction.controller.model.DirectionResponse
import org.delivery.takeout.domain.direction.service.DirectionService
import org.delivery.takeout.domain.store.service.StoreTakeoutService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class DirectionApiControllerTest extends Specification {

    private MockMvc mvc
    private StoreTakeoutService storeTakeoutService = Mock()
    private DirectionService directionService = Mock()
    private List<DirectionResponse> directionResponseList

    def setup(){
        mvc = MockMvcBuilders.standaloneSetup(new DirectionApiController(storeTakeoutService,directionService)).build()

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

    def "searchDirection(shortUrl) - GET"(){
        given:
        String encodedId = "3H6"
        String redirectUrl = "https://map.kakao.com/link/map/미후,38.11,128.11"

        when:
        directionService.searchDirectionById(encodedId) >> redirectUrl
        def result = mvc.perform(get("/api/take-out/map/dir/{encodedId}",encodedId))

        then:
        result.andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(redirectUrl))
            .andDo(print())
    }

    def "searchRoadMap(shortUrl) - GET"(){
        given:
        String encodedId = "3H6"
        String redirectUrl = "https://map.kakao.com/link/roadview/미후,38.11,128.11"

        when:
        directionService.searchRoadViewById(encodedId) >> redirectUrl
        def result = mvc.perform(get("/api/take-out/road-view/dir/{encodedId}",encodedId))

        then:
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl))
                .andDo(print())
    }
}
