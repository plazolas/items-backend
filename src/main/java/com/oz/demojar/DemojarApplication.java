package com.oz.demojar;

import com.oz.demojar.mysqlDatasource.CountryRepository;
import com.oz.demojar.mysqlDatasource.PassportRepository;
import com.oz.demojar.mysqlDatasource.PersonRepository;
import com.oz.demojar.mysqlDatasource.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DemojarApplication {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private PassportRepository passportRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemojarApplication.class, args);
	}

}
