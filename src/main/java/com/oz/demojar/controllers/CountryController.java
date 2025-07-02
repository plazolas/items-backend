package com.oz.demojar.controllers;

import com.oz.demojar.model.Country;
import com.oz.demojar.service.CountryService;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;
@Slf4j
@CrossOrigin(maxAge = 3600)
@RequestMapping("api/vi/country")
@RestController
public class CountryController {

    @Autowired
    private transient CountryService countryService;

    @Autowired
    public CountryController() {}

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Country> selectCountryById(@PathVariable("id") Long id) throws Exception {
       Country country = countryService.getCountryById(id)
               .orElseThrow((() -> new ObjectNotFoundException("Country " + id + " does not exits.")));
       return ResponseEntity.ok().body(country);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Country>> selectAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        return ResponseEntity.ok().body(countries);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<Integer> updateCountry(@RequestBody Country country) {
        Integer updated = countryService.updateCountry(country);
        return ResponseEntity.ok().body(updated);
    }

}
