package org.delivery.takeout.domain.direction.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.annotation.UserSession;
import org.delivery.takeout.domain.direction.controller.model.DirectionRequest;
import org.delivery.takeout.domain.direction.controller.model.User;
import org.delivery.takeout.domain.direction.service.DirectionService;
import org.delivery.takeout.domain.store.service.StoreTakeoutService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/take-out")
@RequiredArgsConstructor
@Slf4j
public class DirectionApiController {

    private final StoreTakeoutService storeTakeoutService;
    private final DirectionService directionService;

    @GetMapping("/")
    public ModelAndView main(@Parameter(hidden = true) @UserSession User user){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("main");
        return mvc;
    }

    @PostMapping("/search")
    public ModelAndView search(
            @Parameter(hidden = true) @UserSession User user,
            @ModelAttribute DirectionRequest request){
        var result = storeTakeoutService.storeTakeoutList(request.getAddress());
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("directionList");
        mvc.addObject("directionList",result);
        return mvc;
    }

    @GetMapping("/map/dir/{encodedId}")
    public ModelAndView searchDirection(@PathVariable("encodedId") String encodedId){

        var result = directionService.searchDirectionById(encodedId);
        log.info("searchDirection: {}",result);
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("redirect:" + result);
        return mvc;
    }

    @GetMapping("/road-view/dir/{encodedId}")
    public ModelAndView searchRoadView(@PathVariable("encodedId")String encodedId){

        var result = directionService.searchRoadViewById(encodedId);
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("redirect:" + result);
        return mvc;
    }
}
