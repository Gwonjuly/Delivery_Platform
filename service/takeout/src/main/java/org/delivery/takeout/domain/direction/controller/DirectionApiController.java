package org.delivery.takeout.domain.direction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.takeout.domain.direction.controller.model.DirectionRequest;
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
    public ModelAndView main(){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("main");
        return mvc;
    }

    @PostMapping("/search")
    public ModelAndView search(@ModelAttribute DirectionRequest request){
        var result = storeTakeoutService.storeTakeoutList(request.getAddress());
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("directionList");
        mvc.addObject("directionList",result);
        return mvc;
    }

    @GetMapping("/dir/{encodedId}")
    public ModelAndView searchDirection(@PathVariable("encodedId") String encodedId){

        var result = directionService.searchDirectionById(encodedId);
        log.info("searchDirection: {}",result);
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("redirect:" + result);
        return mvc;
    }
}
