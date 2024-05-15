package org.delivery.storeadmin.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("")//로그인 시,
public class PageController {

    @RequestMapping(path = {"","/main"})//기본과 main 2개의 주소르 받음
    public ModelAndView main(){
        return new ModelAndView("main");// main.html과 mapping
    }

    @RequestMapping(path = "/order")
    public ModelAndView order(){
        return new ModelAndView("/order/order");//order 패키지의 order.html과 mapping
    }
}
