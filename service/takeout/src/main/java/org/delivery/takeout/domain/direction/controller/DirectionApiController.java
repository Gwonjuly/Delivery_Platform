package org.delivery.takeout.domain.direction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.direction.DirectionEntity;
import org.delivery.takeout.domain.direction.controller.model.DirectionRequest;
import org.delivery.takeout.domain.direction.service.Base62Service;
import org.delivery.takeout.domain.direction.service.DirectionService;
import org.delivery.takeout.domain.store.service.StoreTakeoutService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/take-out")
@RequiredArgsConstructor
@Slf4j
public class DirectionApiController {

    private final StoreTakeoutService storeTakeoutService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    private static final String DIRECTION_MAP_DEFAULT_URL = "https://map.kakao.com/link/MAP/";

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
        DirectionEntity directionEntity = directionService.findById(encodedId);

        String params = String.join(",", directionEntity.getTargetStoreName(),
                String.valueOf(directionEntity.getTargetLatitude()),
                String.valueOf(directionEntity.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_MAP_DEFAULT_URL + params)
                .toUriString();

        log.info("direction params: {}, url:{}",params,result);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("redirect:" + result);
        return mvc;
    }
}
