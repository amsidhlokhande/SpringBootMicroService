package com.amsidh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.retry.support.RetryTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@AllArgsConstructor
@Slf4j
public class RetryableConfig {
	
	private final Environment environment;
	
 @Bean
 public RetryTemplate getRetryTemplate() {
	 log.info("retry.max.retry.attempt :"+ Integer.parseInt(environment.getProperty("retry.max.retry.attempt")));
	 log.info("retry.max.backoff.mills :"+ Long.parseLong(environment.getProperty("retry.max.backoff.mills")));
	 return RetryTemplate.builder()
				.maxAttempts(Integer.parseInt(environment.getProperty("retry.max.retry.attempt")))
				.fixedBackoff(Long.parseLong(environment.getProperty("retry.max.backoff.mills")))
				.retryOn(Exception.class)
				.traversingCauses()
				.build();
 }
 
}
