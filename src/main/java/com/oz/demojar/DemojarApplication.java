package com.oz.demojar;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;

import org.springframework.boot.ExitCodeGenerator;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Use the @ComponentScan annotation along with the @Configuration annotation to specify the packages that we want to be scanned.
// @ComponentScan without arguments tells Spring to scan the current package and all of its sub-packages.
// Note that the main DemojarApplication class is also a bean, as it's annotated with @Configuration, which is a @Component.

// The trick with Spring Boot is that many things happen implicitly.
// We use the @SpringBootApplication annotation, but it's a combination of three annotations:
// @Configuration
// @EnableAutoConfiguration
// @ComponentScan


@Slf4j
@SpringBootApplication
@EnableJpaRepositories
public class DemojarApplication {
	private static ApplicationContext applicationContext;

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> 0;
	}

	// System.setProperty("server.servlet.context-path", "/myapp");
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(DemojarApplication.class, args);
		List<String> partial = Arrays.asList(
				"Controller",
				"Service",
				"erv"
		);
		// listBeansPresence(partial);

	}

	@Bean
	ApplicationRunner applicationRunner(Environment environment) {
		return args -> {
			String[] profiles = environment.getActiveProfiles();
			if (profiles.length == 0) {
				profiles = environment.getDefaultProfiles();
			}
			for (String profile : profiles) {
				log.info(profile);
			}

			log.info("Environment: " + environment.getProperty("spring.config.activate.on-profile"));
			log.info("Datasource:  " + environment.getProperty("spring.datasource.url"));
			log.info("DevTools:  "   + environment.getProperty("spring.devtools.restart.enabled"));
		};
	}

	private static void listBeansPresence(List<String> partials) {
		String[] beans = applicationContext.getBeanDefinitionNames();
		for (String dependency : partials) {
			for (String beanName : beans) {
				if (beanName.contains(dependency))
					log.info(beanName);
			}
		}
	}

}
