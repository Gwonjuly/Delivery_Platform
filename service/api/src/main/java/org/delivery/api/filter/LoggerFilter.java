package org.delivery.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //실질적으로 컨트롤러에서 받는 request와 response를 변환 req, res가 받게 됨
        //ServletRequest 형태의 request와 response를 HttpServletRequest 형태로 형 변환
        var req=new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res=new ContentCachingResponseWrapper((HttpServletResponse) response);
        //실행 전: request 들어옴

        log.info("INIT URI: {}",req.getRequestURI());

        chain.doFilter(req,res);//실행 기준선
        //실행 후: response 나감

        //request 정보 수집(해더(키:값))
        var headerNames=req.getHeaderNames();//header 정보 수집
        var headerValues=new StringBuilder();
        
        headerNames.asIterator().forEachRemaining(headerKey ->{
            var headerValue=req.getHeader(headerKey);
            // 헤더1 키:헤더1  값, 헤더2 키, 헤더2 값
            headerValues.append("(").append(headerKey).append(":").append(headerValue).append(")").append(",");
        });

        //req.getContentAsByteArray();//content의 내용을 읽어옴
        var requestBody=new String(req.getContentAsByteArray());
        var uri=req.getRequestURI();
        var method=req.getMethod();

        log.info(">>>>> uri: {}, method: {},header: {}, body:{}", uri, method, headerValues, requestBody);//req에는 body 없음

        //response 정보
        var responseHeaderValues=new StringBuilder();
        res.getHeaderNames().forEach(headerKey->{
            var headerValue=res.getHeader(headerKey);
            responseHeaderValues.append("(").append(headerKey).append(":").append(headerValue).append(")").append(",");
        });

        var responseBody=new String(res.getContentAsByteArray());
        log.info("<<<<< uri:{} , method: {}, header:{},body:{}", uri, method, responseHeaderValues,responseBody);//res에는 header 없음

        res.copyBodyToResponse();//responseBody를 위에서 이미 읽어왔기 때문에 한번 더 읽어오도록 하는 메서드 필요(필수임)
    }
}
