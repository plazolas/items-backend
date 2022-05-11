package com.oz.demojar.service;

import com.oz.demojar.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CountryService {

    List<Country> getAllCountries();

    Optional<Country> getCountryById(Long id);
}
