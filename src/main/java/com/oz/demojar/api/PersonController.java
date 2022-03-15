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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RequestMapping("api/vi/person")
@RestController
public class PersonController {

    private final String cors="http://www.ozdev.net";
    private final PersonService personService;
    private final CountryService countryService;
    private final HttpServletRequest request;
    //private final Validator validator;
    
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
        if (ip.chars().filter($ -> $ == '.').count() != 3) ip = "Illegal IP";
        return ip;
    }

    @Autowired
    public PersonController(PersonService personService, CountryService countryService, HttpServletRequest httpRequest) {
        this.personService = personService;
        this.countryService = countryService;
        this.request = httpRequest;

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //this.validator = factory.getValidator();
    }

    @PostMapping  (path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {

        Person savedPerson = personService.addPerson(person.getFirstName(), person.getLastName(),
                person.getCountry(), person.getPosition(), person.getAge(), person.getBoss());
        if(savedPerson instanceof Person) {
            return new ResponseEntity<Person>(savedPerson, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Person> getAllPeople() {
	String ip = this.getIpAddress(this.request);    
        System.out.println("request from address: " + ip);
        List<Person> persons = personService.getAllPeople();
        return persons;
    }

    @CrossOrigin(origins = cors)
    @GetMapping(path = "/anns")
    public List<Person> getAllAnns() {
        Collection<Person> persons = personService.findAllAnns();
        return persons.stream().collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")
    public Person selectPersonById(@PathVariable("id") Long id) {
        if(id.equals("undefined")) { System.out.println("undefined");return null; }
        System.out.println(id);
        return personService.getPersonById(id);
    }

    @DeleteMapping(path = "{id}")
    public int deletePersonById(@PathVariable("id") Long id) {
        return personService.deletePersonById(id);
    }

    @PutMapping(path = "{id}")
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
    public long findLastId() {
        Long id = personService.findLastId();
        System.out.println();
        return id;
    }
}
