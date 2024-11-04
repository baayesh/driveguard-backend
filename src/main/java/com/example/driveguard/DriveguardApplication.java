package com.example.driveguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.driveguard.Domain.entity")

public class DriveguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriveguardApplication.class, args);
	}

}
