package org.delivery.takeout.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

        var req=new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res=new ContentCachingResponseWrapper((HttpServletResponse) response);
        //실행 전: request 들어옴

        log.info("INIT URI: {}",req.getRequestURI());

        chain.doFilter(req,res);//실행 기준선
        //실행 후: response 나감

        var headerNames=req.getHeaderNames();//header 정보 수집
        var headerValues=new StringBuilder();
        
        headerNames.asIterator().forEachRemaining(headerKey ->{
            var headerValue=req.getHeader(headerKey);

            headerValues.append("(").append(headerKey).append(":").append(headerValue).append(")").append(",");
        });

        var requestBody=new String(req.getContentAsByteArray());
        var uri=req.getRequestURI();
        var method=req.getMethod();

        log.info(">>>>> uri: {}, method: {},header: {}, body:{}", uri, method, headerValues, requestBody);

        //response 정보
        var responseHeaderValues=new StringBuilder();
        res.getHeaderNames().forEach(headerKey->{
            var headerValue=res.getHeader(headerKey);
            responseHeaderValues.append("(").append(headerKey).append(":").append(headerValue).append(")").append(",");
        });

        var responseBody=new String(res.getContentAsByteArray());
        log.info("<<<<< uri:{} , method: {}, header:{},body:{}", uri, method, responseHeaderValues,responseBody);

        res.copyBodyToResponse();
    }
}
