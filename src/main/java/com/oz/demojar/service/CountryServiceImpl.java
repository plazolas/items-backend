package com.oz.demojar.service;

import com.oz.demojar.dao.CountryDao;
import com.oz.demojar.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CountryServiceImpl implements CountryService { //  throws InvalidDataAccessApiUsageException, NoSuchElementFoundException

    @Autowired
    private static CountryDao countryDao;

    public List<Country> getAllCountries() {
        return countryDao.selectAllCountries();
    }

    public Optional<Country> getCountryById(Long id) {
        return countryDao.getCountryById(id);
    }

}
