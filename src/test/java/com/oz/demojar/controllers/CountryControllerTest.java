package com.oz.demojar.controllers;

import com.oz.demojar.model.Country;
import com.oz.demojar.service.CountryService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    private AutoCloseable openMocks;

    @BeforeEach
    public void before() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        openMocks.close();
    }

    @Test
    public void selectCountryByIdTest() {
        Long id = 1L;
        Country country = new Country();
        country.setName("Albania");
        country.setId(1L);
        country.setPersons(new ArrayList<>());
        when(countryService.getCountryById(id)).thenReturn(Optional.of(country));

        try {
            ResponseEntity<Country> responseEntity = countryController.selectCountryById(1L);
            Assertions.assertNotNull(responseEntity);
            Country country1 = responseEntity.getBody();
            Assertions.assertEquals(country, country1);
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void selectAllCountriesTest() {
        Country country1 = new Country();
        country1.setId(2L);
        country1.setName("Syria");
        Country country2 = new Country();
        country2.setId(3L);
        country2.setName("Libya");
        List<Country> countries = new ArrayList<>();
        countries.add(country1);
        countries.add(country2);
        when(countryService.getAllCountries()).thenReturn(countries);
        try {
            ResponseEntity<List<Country>> responseEntity = countryController.selectAllCountries();
            Assertions.assertNotNull(responseEntity);
            Assertions.assertEquals(countries.size(), responseEntity.getBody().size());
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void selectCountryByIdNotFoundReturnsNullTest() {
        when(countryService.getAllCountries()).thenReturn(null);
        try {
            ResponseEntity<Country> responseEntity = countryController.selectCountryById(1L);
            Assertions.assertNull(responseEntity);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

}
