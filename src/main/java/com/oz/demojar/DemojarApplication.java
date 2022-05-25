package com.oz.demojar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DemojarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemojarApplication.class, args);
	}

}
