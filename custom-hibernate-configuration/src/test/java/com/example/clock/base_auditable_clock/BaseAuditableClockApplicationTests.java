package com.example.clock.base_auditable_clock;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import({TestcontainersConfiguration.class, ClockConfigurationForTest.class})
@SpringBootTest
@ActiveProfiles("test")
class BaseAuditableClockApplicationTests {

	@Autowired
	private LotService lotService;

	@Autowired
	private FooService fooService;



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

	@Test
	void foos_have_auditable_attrs() {
		var foo = fooService.saveOne();

		Assertions.assertEquals(Instant.parse("1996-06-16T07:10:43.004684600Z"), foo.getCreatedOn());
		Assertions.assertEquals("ALEX", foo.getCreatedBy());
		Assertions.assertEquals("ALEX", foo.getLastUpdatedBy());
		Assertions.assertEquals(Instant.parse("1996-06-16T07:10:43.004684600Z"), foo.getLastUpdatedOn());
		return;
	}



}
