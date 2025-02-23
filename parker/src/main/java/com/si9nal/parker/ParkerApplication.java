package com.si9nal.parker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ParkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkerApplication.class, args);
	}

}
