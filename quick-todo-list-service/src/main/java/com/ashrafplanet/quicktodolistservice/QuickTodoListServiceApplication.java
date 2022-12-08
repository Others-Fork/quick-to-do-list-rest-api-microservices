package com.ashrafplanet.quicktodolistservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ashrafplanet.quicktodolistservice.feign.FeignConfiguration;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class QuickTodoListServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickTodoListServiceApplication.class, args);
	}
}
