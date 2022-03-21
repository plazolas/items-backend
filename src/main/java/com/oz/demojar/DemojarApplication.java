package com.oz.demojar;

import com.oz.demojar.mysqlDatasource.CountryRepository;
import com.oz.demojar.mysqlDatasource.PassportRepository;
import com.oz.demojar.mysqlDatasource.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class DemojarApplication {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private PassportRepository passportRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemojarApplication.class, args);
	}

}
