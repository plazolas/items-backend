package com.oz.demojar.api;

import com.oz.demojar.model.Country;
import com.oz.demojar.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/vi/country")
@RestController
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping(path = "{id}")
    public Country selectCountryById(@PathVariable("id") String id) {
        Long lid = Long.getLong(id);
        return countryService.getCountryById(lid);
    }
}
