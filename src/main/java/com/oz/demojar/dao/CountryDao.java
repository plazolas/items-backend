package com.oz.demojar.dao;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Person;

import java.util.List;
import java.util.Optional;

public interface CountryDao {

    List<Country> selectAllCountries();

    int deleteCountryById(Long id);

    Optional<Country> getCountryById(Long id);

}
