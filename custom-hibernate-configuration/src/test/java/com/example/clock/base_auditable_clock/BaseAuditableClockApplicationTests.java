package com.example.clock.base_auditable_clock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class BaseAuditableClockApplicationTests {

	@Autowired
	private LotService lotService;

	@Test
	void contextLoads() {
	}

	@Test
	void base_auditable_date_is_save_automatically() {
		var lot = lotService.saveOne();

		Assertions.assertNotNull(lot.getCreatedOn());
		Assertions.assertEquals("ALEX", lot.getCreatedBy());
		Assertions.assertEquals("ALEX", lot.getLastUpdatedBy());
		return;
	}

}
