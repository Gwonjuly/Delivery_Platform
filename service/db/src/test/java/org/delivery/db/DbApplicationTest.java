package org.delivery.db;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.delivery.db.review")
@EntityScan(basePackages = "org.delivery.db.review")
public class DbApplicationTest {
}
