package com.example.Resilience5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Resilience5Application {

	public static void main(String[] args) {
		SpringApplication.run(Resilience5Application.class, args);
	}


	@Bean
	public RestTemplate getRestTemplate() {

		return new RestTemplate();
	}


}
