package com.amsidh.mvc.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.amsidh.mvc.handler.signin.model.SignInRequestModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JwtUtil {

	private Environment environment;

	public String generateToken(SignInRequestModel signInRequestModel) {
		log.debug("JwtUtil authenticate generateToken called");
		Date now = new Date();
		Map<String, Object> claims = new HashMap<>();
		claims.put("alg", "HS512");
		claims.put("typ", "JWT");

		return Jwts.builder().setHeaderParams(claims).setSubject(signInRequestModel.getUsername())
				.signWith(SignatureAlgorithm.HS512,
						Base64.getEncoder().encodeToString(this.environment.getProperty("jwt.token.salt").getBytes()))
				.setIssuedAt(now).setExpiration(new Date(now.getTime()
						+ Long.parseLong(this.environment.getProperty("jwt.token.expiration.time_millis"))))
				.compact();
	}

	public Claims getClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(
						Base64.getEncoder().encodeToString(this.environment.getProperty("jwt.token.salt").getBytes()))
				.parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDate(String token) {
		return getClaimsFromToken(token).getExpiration();
	}

	public Boolean isTokenExpired(String token) {
		return getExpirationDate(token).before(new Date());
	}

	public Boolean isTokenValidated(String token) {
		return !isTokenExpired(token);
	}

}
