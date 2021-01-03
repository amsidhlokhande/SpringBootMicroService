package com.amsidh.mvc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amsidh.mvc.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final Environment environment;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public WebSecurityConfig(Environment environment, UserService userService,
			PasswordEncoder passwordEncoder) {
		this.environment = environment;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip.address"))
		.and()
		.addFilter(getMyAuthenticationFilter());
		http.headers().frameOptions().disable();
	}

	private MyAuthenticationFilter getMyAuthenticationFilter() throws Exception {
		MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter(environment, userService);
		myAuthenticationFilter.setAuthenticationManager(authenticationManager());
		myAuthenticationFilter.setFilterProcessesUrl(this.environment.getProperty("login.url.path"));
		return myAuthenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("userService= "+userService+" And passwordEncoder= " +passwordEncoder);
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);

	}

}
