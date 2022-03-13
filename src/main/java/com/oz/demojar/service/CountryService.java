package com.oz.demojar.service;

import com.oz.demojar.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

    List<Country> getAllCountries();

    Country getCountryById(Long id);
}
