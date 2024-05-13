package org.delivery.storeadmin.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.delivery.db")//이 패키지 하위에 @가 있는 것들을 스캔
@EnableJpaRepositories(basePackages = "org.delivery.db")
public class JpaConfig {
}
