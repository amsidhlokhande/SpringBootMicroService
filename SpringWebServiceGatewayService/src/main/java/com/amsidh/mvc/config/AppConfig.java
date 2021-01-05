package com.amsidh.mvc.config;

import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public HttpTraceRepository getHttpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
	
	@Bean
	public AuditEventRepository getAuditEventRepository() {
		return new InMemoryAuditEventRepository();
	}
	

	
}
