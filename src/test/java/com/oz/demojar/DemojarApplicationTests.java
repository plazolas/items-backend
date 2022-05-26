package com.oz.demojar;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.CountryService;
import com.oz.demojar.service.PersonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
// @ContextConfiguration(classes = DemojarApplicationTests.class, locations={"classpath*:resources/META-INF/persistence.xml"})
@ActiveProfiles(value="dev")
// @DataJpaTest
class DemojarApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(DemojarApplicationTests.class);

	@Autowired
	private EntityManager em;

	@Autowired
	private CountryService countryService;

	@Autowired
	private PersonService personService;

	@BeforeEach
	void setUp() {
		// this.countryService = new CountryService();
	}

	@Test
	@DisplayName("All countries should be fetched")
	public void testGetAllCountries() {
		List<Country> countries = this.countryService.getAllCountries();
		log.info(String.valueOf(countries.size()));
		assertEquals(12, countries.size(),
				"All countries were included");
	}

	@Test
	@DisplayName("All items should be fetched")
	public void testGetAllItems() {
		List<Person> person = this.personService.getAllPeople();
		log.info(String.valueOf(person.size()));
		assertEquals(61, person.size(),
				"All items were included");
	}

}
