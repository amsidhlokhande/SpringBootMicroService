package com.amsidh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryableConfig {
	
 @Bean
 public RetryTemplate getRetryTemplate() {
	 return RetryTemplate.builder()
				.maxAttempts(3)
				.fixedBackoff(5000)
				.retryOn(Exception.class)
				.traversingCauses()
				.build();
 }

}
