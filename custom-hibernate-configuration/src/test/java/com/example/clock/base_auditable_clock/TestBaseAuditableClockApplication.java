package com.example.clock.base_auditable_clock;

import org.springframework.boot.SpringApplication;

public class TestBaseAuditableClockApplication {

	public static void main(String[] args) {
		SpringApplication.from(BaseAuditableClockApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
