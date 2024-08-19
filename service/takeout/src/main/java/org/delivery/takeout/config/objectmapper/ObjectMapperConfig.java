package org.delivery.takeout.config.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){

        var objectMapper=new ObjectMapper();
        return objectMapper;
    }
}
