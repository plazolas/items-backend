package com.oz.demojar.api;

import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("api/vi/passport")
@RestController
public class PassportController {

    private final PassportService passportService;

    @Autowired
    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping  // (value="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Passport createPassport(@RequestBody Person person) {
        Passport p = passportService.createPassport(person);
        System.out.println(p);
        return p;
    }

    @GetMapping
    public List<Passport> getAllPassports() {
        return passportService.getAllPassports();
    }

    @GetMapping(path = "{id}")
    public Passport selectPassportById(@PathVariable("id") String id) {
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
