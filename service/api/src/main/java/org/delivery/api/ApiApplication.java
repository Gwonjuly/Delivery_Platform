package org.delivery.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {//spring boot의 main application(원래 자동으로 만들어짐)

    public static void main(String args[]){
        SpringApplication.run(ApiApplication.class,args);
    }
}
