package org.delivery.storeadmin;

import org.delivery.db.storemenu.StoreMenuEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StoreAdminApplication {
    public static void main(String[] args){
        SpringApplication.run(StoreAdminApplication.class,args);
    }
}
