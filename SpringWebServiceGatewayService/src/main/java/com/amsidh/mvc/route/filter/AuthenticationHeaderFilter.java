package com.amsidh.mvc.route.filter;

import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationHeaderFilter extends AbstractGatewayFilterFactory<Object> {

	private final Environment environment;

	public AuthenticationHeaderFilter(Environment environment) {
		this.environment = environment;
	}

	@Override
	public GatewayFilter apply(Object config) {
		log.info("GatewayFilter apply method called");
		return (exchange, chain) -> {
			HttpHeaders headers = exchange.getRequest().getHeaders();
			if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization header found", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeaderValue = headers.get(HttpHeaders.AUTHORIZATION).get(0);
			String jwtTokenValue = authorizationHeaderValue.replace("Bearer", "").trim();
			log.info("jwtTokenValue " + jwtTokenValue);
			if (!isJwtValid(jwtTokenValue)) {
				log.error("Invalid JWT Token");
				return onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
		log.error(error);
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private boolean isJwtValid(String jwtToken) {
		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(environment.getProperty("jwt.token.salt")).parseClaimsJws(jwtToken).getBody()
					.getSubject();
			return Optional.ofNullable(subject).map(sub -> true).orElse(false);
		} catch (Exception e) {
			return false;
		}

	}
}
