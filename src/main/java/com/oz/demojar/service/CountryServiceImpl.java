package com.oz.demojar.service;

import com.oz.demojar.dao.CountryDao;
import com.oz.demojar.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
class CountryServiceImpl implements CountryService { //  throws InvalidDataAccessApiUsageException, NoSuchElementFoundException

    @Autowired
    private CountryDao countryRepo;

    @Autowired
    private void PersonServiceImpl(CountryDao countryDao) {
        this.countryRepo = countryDao;
    }

    public List<Country> getAllCountries() {
        return countryRepo.selectAllCountries();
    }

    public Country getCountryById(Long id) {
        return (countryRepo.getCountryById(id).isPresent()) ? countryRepo.getCountryById(id).get() : null;
    }
}
