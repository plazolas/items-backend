package com.oz.demojar.service;

import com.oz.demojar.dao.CountryDao;
import com.oz.demojar.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Qualifier("country")
    private final CountryDao countryDao;

    @Autowired
    public CountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public List<Country> getAllCountries() {
        return countryDao.selectAllCountries();
    }

    public Country getCountryById(Long id) {
        return (countryDao.getCountryById(id).isPresent()) ? countryDao.getCountryById(id).get() : null;
    }
}
