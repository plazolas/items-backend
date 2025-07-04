package com.oz.demojar.controllers;

import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.PassportService;
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
import java.util.List;
@Slf4j
@CrossOrigin(maxAge = 3600)
@RequestMapping("api/vi/passport")
@RestController
public class PassportController {

    @Autowired
    private transient PassportService passportService;

    @Autowired
    public PassportController() {}

    @PostMapping  // (value="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Passport createPassport(@RequestBody Person person) {
        Passport p = passportService.createPassport(person);
        return p;
    }

    @GetMapping
    public List<Passport> getAllPassports() {
        return passportService.getAllPassports();
    }

    @GetMapping(path = "{id}")
    public Passport getPassportById(@PathVariable("id") String id) {
        Long lid = Long.getLong(id);
        return passportService.selectPassportById(lid);
    }

    @DeleteMapping(path = "{id}")
    public void deletePassportById(@PathVariable("id") String id) {
        Long lid = Long.getLong(id);
        passportService.deletePassportById(lid);
    }

    @PutMapping(path = "{id}")
    public int updatePassportById(@PathVariable("id") Long id, @RequestBody Passport passport) {
        passportService.updatePassportById(id, passport);
        return 1;
    }

}
