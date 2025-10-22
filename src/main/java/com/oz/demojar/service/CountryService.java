package com.oz.demojar.service;

import com.oz.demojar.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    List<Country> getAllCountries();

    Optional<Country> getCountryById(Long id);


    int updateCountry(Country country);

    Country saveCountry(Country country);
}
