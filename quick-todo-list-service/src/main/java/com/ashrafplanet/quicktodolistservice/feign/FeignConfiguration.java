package com.ashrafplanet.quicktodolistservice.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;

@Configuration
public class FeignConfiguration {

	@Bean
	public Contract contract() {
		return new Contract.Default();
	}
}
