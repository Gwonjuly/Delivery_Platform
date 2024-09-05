package org.delivery.takeout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
//@ConfigurationPropertiesScan
public class TakeOutApplication {
    public static void main(String[] args){
        SpringApplication.run(TakeOutApplication.class, args);
    }
}
