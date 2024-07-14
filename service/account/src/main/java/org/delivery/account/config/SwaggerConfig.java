package org.delivery.account.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    //스프링: find bean -> ObjectMapper Bean 호출, 이때 호출 되는 빈은 ObjectMapperConfig의 objectMapper
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper){
        return new ModelResolver(objectMapper);//modelresolever는 인자가 objectmapper로 정해져 있음
    }
}
