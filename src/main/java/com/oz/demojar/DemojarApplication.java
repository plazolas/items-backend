package com.oz.demojar;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


// Use the @ComponentScan annotation along with the @Configuration annotation to specify the packages that we want to be scanned.
// @ComponentScan without arguments tells Spring to scan the current package and all of its sub-packages.
// Note that the main DemojarApplication class is also a bean, as it's annotated with @Configuration, which is a @Component.

// The trick with Spring Boot is that many things happen implicitly.
// We use the @SpringBootApplication annotation, but it's a combination of three annotations:
// @Configuration
// @EnableAutoConfiguration
// @ComponentScan


@Log4j2
@SpringBootApplication
@EnableJpaRepositories
public class DemojarApplication {
	private static ApplicationContext applicationContext;

	// System.setProperty("server.servlet.context-path", "/myapp");
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(DemojarApplication.class, args);
		listBeansPresence("springframework");
	}

	@Bean
	ApplicationRunner applicationRunner(Environment environment) {
		return args -> {
			String[] profiles = environment.getActiveProfiles();
			if (profiles.length == 0) {
				profiles = environment.getDefaultProfiles();
			}
			for (int i = 0; i < profiles.length; i++) {
				System.out.println(profiles[i]);
			}

			log.info("Environment: " + environment.getProperty("spring.config.activate.on-profile"));
			log.info("Datasource:  " + environment.getProperty("spring.datasource.url"));
		};
	}

	private static void listBeansPresence(String partial) {
		String[] beans = applicationContext.getBeanDefinitionNames();
		for (String beanName : beans) {
			if(!beanName.contains(partial)) System.out.println(beanName);
		}
	}

}
