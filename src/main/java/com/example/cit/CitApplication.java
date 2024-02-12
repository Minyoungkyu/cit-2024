package com.example.cit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitApplication.class, args);
	}

}
