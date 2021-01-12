package com.amsidh.mvc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class WebFluxJwtSecurityConfig {

    final List<String> whiteListIp;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final Environment environment;

    public WebFluxJwtSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository, Environment environment) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.environment = environment;
        whiteListIp = Arrays.asList(environment.getProperty("gateway.ip.address"));
    }


    @Bean
    public SecurityWebFilterChain getSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        String[] properties = this.environment.getProperty("security.permitAll.paths", String[].class);
        Arrays.asList(properties).forEach(System.out::println);

        log.info("WebFluxJwtSecurityConfig getSecurityFilterChain method called");
        serverHttpSecurity.authorizeExchange()
                .pathMatchers(properties).permitAll()
                .pathMatchers("/users/signIn").permitAll()
                .pathMatchers("/users/signUp").permitAll()
                .pathMatchers("/users/health/check").permitAll()
                .anyExchange().authenticated()
                /*.access(this::whiteListIp)*/
                .and()
                .formLogin().disable()
                .httpBasic().authenticationEntryPoint(new HttpStatusServerEntryPoint(UNAUTHORIZED))
                .and()
                .csrf().disable()
                .authenticationManager(authenticationManager).securityContextRepository(securityContextRepository)
                .requestCache().requestCache(NoOpServerRequestCache.getInstance());

        return serverHttpSecurity.build();
    }


    private Mono<AuthorizationDecision> whiteListIp(Mono<Authentication> authentication, AuthorizationContext context) {
        log.info("WebFluxJwtSecurityConfig whiteListIp method called");
        String ip = context.getExchange().getRequest().getRemoteAddress().getAddress().toString().replace("/", "");
        return authentication.map((a) -> new AuthorizationDecision(a.isAuthenticated()))
                .defaultIfEmpty(new AuthorizationDecision(
                        (whiteListIp.contains(ip)) ? true : false
                ));
    }
}


