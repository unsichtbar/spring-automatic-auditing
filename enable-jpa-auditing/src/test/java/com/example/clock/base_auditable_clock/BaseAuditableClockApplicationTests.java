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

		Assertions.assertTrue(lot.getCreatedDate().isPresent());
		Assertions.assertEquals("ALEX", lot.getCreatedBy().get());
		Assertions.assertEquals("ALEX", lot.getLastModifiedBy().get());
		return;
	}

}
