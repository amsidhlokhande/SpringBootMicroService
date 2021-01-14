package com.amsidh.mvc.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class Resilience4JCircuitBreakerConfiguration {

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
		return factory -> factory.configure(builder -> builder.timeLimiterConfig(this.getTimeLimiterConfig())
				.circuitBreakerConfig(this.getCircuitBreakerConfig()).build(), "albumsCircuitBreaker");
	}

	@Bean
	public CircuitBreakerConfig getCircuitBreakerConfig() {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom().failureRateThreshold(50)
				.waitDurationInOpenState(Duration.ofMillis(1000)).slidingWindowSize(2).build();
		return circuitBreakerConfig;
	}

	@Bean
	public TimeLimiterConfig getTimeLimiterConfig() {
		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build();
		return timeLimiterConfig;
	}
}
