package com.example.clock.base_auditable_clock;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class ClockConfigurationForTest {

    @Bean
    @Profile("test")
    Clock clock() {
        return Clock.fixed(Instant.parse("1996-06-16T07:10:43.004684600Z"), ZoneId.of("America/New_York"));
    }
}
