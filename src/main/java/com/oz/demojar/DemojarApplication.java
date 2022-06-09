package com.oz.demojar;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Log4j2
@SpringBootApplication
@EnableJpaRepositories
@ComponentScan
public class DemojarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemojarApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(Environment environment) {
		return args -> {
			log.info("Environment: " + environment.getProperty("spring.config.activate.on-profile"));
			log.info("Datasource:  " + environment.getProperty("spring.datasource.url"));
		};
	}

}
