package org.delivery.takeout.domain.direction.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.takeout.domain.direction.controller.model.DirectionRequest;
import org.delivery.takeout.domain.store.service.StoreTakeoutService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/take-out")
@RequiredArgsConstructor
public class DirectionApiController {

    private final StoreTakeoutService storeTakeoutService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView search(@ModelAttribute DirectionRequest request){
        var result = storeTakeoutService.storeTakeoutList(request.getAddress());
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("directionList");
        mvc.addObject("directionList",result);
        return mvc;
    }
}
