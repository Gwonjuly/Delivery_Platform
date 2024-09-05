package org.delivery.storeadmin.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")//로그인 시,
public class PageController {

    @RequestMapping(path = {"/","/main"})//기본과 main 2개의 주소르 받음
    public ModelAndView main(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        return new ModelAndView("main");// main.html과 mapping
    }
}
