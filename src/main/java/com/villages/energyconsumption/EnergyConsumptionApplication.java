package com.villages.energyconsumption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EnergyConsumptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyConsumptionApplication.class, args);
    }

}
