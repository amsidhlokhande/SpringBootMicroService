package com.amsidh.mvc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.amsidh.mvc.exception.UserException;
import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final Environment environment;
	private final UserService userService;

	public MyAuthenticationFilter(Environment environment, UserService userService) {
		this.environment = environment;
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginRequestModel.getEmailId(), loginRequestModel.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new UserException("Username/Password does not match");
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		log.info("After successful login this method will be called");

		String username = ((UserDetails) authResult.getPrincipal()).getUsername();
		UserDto userDto = this.userService.getUserEntityByEmailId(username);
		log.info("Time in Mills fetched from Config Server "+ Long.parseLong(environment.getProperty("jwt.token.expiration.time_millis")));
		String token = Jwts.builder().setSubject(userDto.getUserId())
				.setExpiration(new Date(System.currentTimeMillis()
						+ Long.parseLong(environment.getProperty("jwt.token.expiration.time_millis"))))
				.signWith(SignatureAlgorithm.HS512, this.environment.getProperty("jwt.token.salt")).compact();

		response.setHeader("token", token);
		response.setHeader("userId", userDto.getUserId());
		
	}

}
