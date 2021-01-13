package com.amsidh.mvc.security;

import com.amsidh.mvc.repository.user.entity.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info("AuthenticationManager authenticate method called");
        String token = authentication.getCredentials().toString();
        String username = jwtUtil.getUsernameFromToken(token);

        return userRepository.findByEmailId(username).flatMap(employee -> {
            log.info("Checking jwt token validation");
			if (employee.getEmailId().equals(username) && jwtUtil.isTokenValidated(token)) {
                log.info("Jwt token is valid");
                return Mono.just(authentication);
            } else {
                log.info("Jwt token is Invalid");
                return Mono.empty();
            }
        }).switchIfEmpty(Mono.empty());
    }
}
