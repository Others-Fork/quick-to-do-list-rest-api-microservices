package com.ashrafplanet.quicktodolistservice.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		// http.authorizeHttpRequests().antMatchers("/swagger-ui/**").permitAll();
		http.authorizeHttpRequests().antMatchers("/").permitAll().anyRequest().authenticated();

		http.httpBasic(withDefaults());
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.csrf().disable();

		return http.build();
	}
}