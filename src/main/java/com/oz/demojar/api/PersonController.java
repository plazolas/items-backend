package com.oz.demojar.api;

import com.oz.demojar.model.Person;
import com.oz.demojar.model.Country;
import com.oz.demojar.service.PersonService;
import com.oz.demojar.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("api/vi/person")
@RestController

public class PersonController {

    private final String cors="http://ozdev.net";
    private final PersonService personService;
    private final CountryService countryService;
    //private final Validator validator;

    @Autowired
    public PersonController(PersonService personService, CountryService countryService) {
        this.personService = personService;
        this.countryService = countryService;

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //this.validator = factory.getValidator();
    }

    @PostMapping  (consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {

        Person savedPerson = personService.addPerson(person.getFirstName(), person.getLastName(), person.getCountry());
        if(savedPerson instanceof Person) {
            return new ResponseEntity<Person>(savedPerson, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @CrossOrigin(origins = cors)
    public List<Person> getAllPeople() {
        List<Person> persons = personService.getAllPeople();
        return persons;
    }

    @GetMapping(path = "/anns")
    public List<Person> getAllAnns() {
        Collection<Person> persons = personService.findAllAnns();
        return persons.stream().collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")
    @CrossOrigin(origins = cors)
    public Person selectPersonById(@PathVariable("id") Long id) {
        if(id.equals("undefined")) { System.out.println("undefined");return null; }
        System.out.println(id);
        return personService.getPersonById(id);
    }

    @DeleteMapping(path = "{id}")
    @CrossOrigin(origins = cors)
    public int deletePersonById(@PathVariable("id") Long id) {
        return personService.deletePersonById(id);
    }

    @PutMapping(path = "{id}")
    @CrossOrigin(origins = cors)
    public Person updatePersonById(@PathVariable("id") Long id, @RequestBody Person person) {
        return personService.updatePersonById(id, person);
    }

    @PutMapping(path = "/p/{pid}/c/{cid}")
    @CrossOrigin(origins = cors)
    public int addPersonToCountry(@PathVariable("pid") Long pid, @PathVariable("cid") Long cid) {
        Person  p = personService.getPersonById(pid);
        Country c = countryService.getCountryById(cid);
        if (p == null || c == null) {
            personService.addPersonToCountry(p, c);
            return 1;
        } else {
            return 0;
        }
    }

    @PutMapping  (path="bycountry/{cid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = cors)
    public List<Person> getPeopleByCountry(@PathVariable("cid") Long cid) {
        System.out.println("cid :"+cid);
        Collection<Person> persons = personService.findPersonsWithPassportsByCountry(cid);
        return persons.stream().collect(Collectors.toList());
    }

    @GetMapping(params = { "page", "size" })
    public List<Person> findPaginated(@RequestParam("page") int page,
                                   @RequestParam("size") int size,
                                   UriComponentsBuilder uriBuilder,
                                   HttpServletResponse response) {
        List<Person> resultPage = personService.findPaginated(page,size);
        return resultPage;
    }

    @GetMapping("/massupdate")
    public List<Person> massUpdate() {
        List<Person> resultPage = personService.massUpdate();
        return resultPage;
    }

    @GetMapping("/findlast")
    @CrossOrigin(origins = cors)
    public long findLastId() {
        Long id = personService.findLastId();
        System.out.println();
        return id;
    }
}
