package com.example.clock.base_auditable_clock;

import java.time.Clock;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing(dateTimeProviderRef="myDateTimeProvider")
public class BaseAuditableClockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseAuditableClockApplication.class, args);
	}

	@Bean
	Clock clock() {
		return Clock.system(ZoneId.of("America/New_York"));
	}

	@Bean
	DateTimeProvider myDateTimeProvider(Clock clock) {
		return new DateTimeProvider() {
			@Override
			public Optional<TemporalAccessor> getNow() {
				return Optional.of(clock.instant());
			}
		};
	}


}
