package com.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CafePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafePracticeApplication.class, args);
	}

}
