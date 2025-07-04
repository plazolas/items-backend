package com.oz.demojar.dao;

import com.oz.demojar.model.Country;
import com.oz.demojar.mysqlDatasource.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@Primary
@Repository("country")
class CountryDaoImpl implements CountryDao {

    @Autowired // This means to get the bean called CountryDataSource Which is auto-generated by Spring, we will use it to handle the data
    private transient CountryRepository countryRepository;

    @Override
    public List<Country> selectAllCountries() {
        return countryRepository.findAll();
    }

    public List<Country> selectAllCountriesSorted() {
        List<Country> countries = countryRepository.findAll();
        countries.sort(Comparator.comparing(c -> c.getName().toLowerCase()));
        return countries;
    }

    @Override
    public int deleteCountryById(Long id) {
        Optional<Country> Op = getCountryById(id);
        if(Op.isPresent()){
            countryRepository.delete(Op.get());
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Optional<Country> findCountryByName(String name) {
        return countryRepository.findByName(name);
    }

    public long findLastId() {
        return countryRepository.findLastId();
    }

    @Override
    public int updateCountry(Country country) {
        return countryRepository.updateCountry(country.getName(), country.getId());
    }
}

