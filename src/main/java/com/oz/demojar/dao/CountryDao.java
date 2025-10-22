package com.oz.demojar.dao;

import com.oz.demojar.model.Country;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CountryDao {

    List<Country> selectAllCountries();

    int deleteCountryById(Long id);

    Optional<Country> getCountryById(Long id);

    int updateCountry(Country country);

    Country saveCountry(Country country);

}
