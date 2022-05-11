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
    private CountryDao countryRepo;

    @Autowired
    private void PersonServiceImpl(CountryDao countryDao) {
        this.countryRepo = countryDao;
    }

    public List<Country> getAllCountries() {
        return countryRepo.selectAllCountries();
    }

    public Optional<Country> getCountryById(Long id) {
        return countryRepo.getCountryById(id);
    }
}
