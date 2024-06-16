package com.example.clock.base_auditable_clock;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan
@EntityScan
public class BaseAuditableClockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseAuditableClockApplication.class, args);
	}

	@Bean
	@Profile("!test")
	Clock clock() {
		return Clock.system(ZoneId.of("America/New_York"));
	}



}
