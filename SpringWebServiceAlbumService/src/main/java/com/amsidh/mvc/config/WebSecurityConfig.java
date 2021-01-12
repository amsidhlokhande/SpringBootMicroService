package com.amsidh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class WebSecurityConfig {
	
	@Bean
	public SecurityWebFilterChain getSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		log.info("WebSecurityConfig getSecurityFilterChain");
		serverHttpSecurity.authorizeExchange().anyExchange().permitAll();
		serverHttpSecurity.csrf().disable();
		return serverHttpSecurity.build();
	}

}
/*
 * @Component class AlbumAuthenticationManager implements
 * ReactiveAuthenticationManager {
 * 
 * @Override public Mono<Authentication> authenticate(Authentication
 * authentication) { // Check the token validity and password matches then just
 * return authentication return null; }
 * 
 * }
 * 
 * @Component class AlbumSecurityContextRepository implements
 * ServerSecurityContextRepository {
 * 
 * @Override public Mono<Void> save(ServerWebExchange exchange, SecurityContext
 * context) { // TODO Auto-generated method stub return Mono.empty(); }
 * 
 * @Override public Mono<SecurityContext> load(ServerWebExchange exchange) {
 * //Create JWT token and return the UsernamePasswordAuthenticationToken return
 * null; }
 * 
 * }
 */