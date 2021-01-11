package com.amsidh.mvc.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public HttpTraceRepository getHttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

    @Bean
    public AuditEventRepository getAuditEventRepository() {
        return new InMemoryAuditEventRepository();
    }

    @Bean
	public GroupedOpenApi employeesOpenApi() {
		String[] paths = { "/users/**" };
		return GroupedOpenApi.builder().group("users").pathsToMatch(paths).build();
	}

}
