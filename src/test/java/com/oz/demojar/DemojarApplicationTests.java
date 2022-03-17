package com.oz.demojar;

import com.oz.demojar.dao.CountryDao;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.CountryService;
import com.oz.demojar.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
// @ContextConfiguration(classes = DemojarApplicationTests.class)
@SpringBootTest
class DemojarApplicationTests {

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
	void testGetAllCountries() {
		List<Country> countries = this.countryService.getAllCountries();
		assertEquals(11, countries.size(),
				"All countries were included");
	}

	@Test
	@DisplayName("All items should be fetched")
	void testGetAllItems() {
		List<Person> person = this.personService.getAllPeople();
		assertEquals(61, person.size(),
				"All items were included");
	}

}
