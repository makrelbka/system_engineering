package com.flowershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.flowershop.model")
@EnableJpaRepositories("com.flowershop.repository")
public class FlowerShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowerShopApplication.class, args);
    }
}
