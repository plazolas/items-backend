package com.oz.demojar.controllers;

import com.oz.demojar.dto.PersonDTO;
import com.oz.demojar.model.Person;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.User;
import com.oz.demojar.service.OrderService;
import com.oz.demojar.service.PersonService;
import com.oz.demojar.service.CountryService;
import com.oz.demojar.service.UserService;
import com.oz.demojar.utils.GetIpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600, origins = {"http://www.ozdev.net"})
@RequestMapping("api/vi/person")
@RestController
@Slf4j
public class PersonController {

    private final String cors = "http://localhost:4200";
    private final HttpServletRequest request;
    @Autowired
    private transient CountryService countryService;
    @Autowired
    private transient UserService userService;
    @Autowired
    private transient PersonService personService;
    @Autowired
    private transient OrderService orderService;

    @Autowired
    public PersonController(HttpServletRequest httpRequest) {
        this.request = httpRequest;

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //this.validator = factory.getValidator();
    }

//      ** WARNING ** --> THIS GENERATES ADMIN USER!
//    @GetMapping(path = "/genadmin")
//    public ResponseEntity<User> genAdmin() {
//
//        User newAdmin = new User(startupProperties.getUsername(), startupProperties.getPassword(), "ROLE_ADMIN" );
//        try {
//            userService.createAdminUser(newAdmin);
//            return new ResponseEntity<User>(newAdmin, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> addPerson(@Valid @RequestBody Person personDTO) {
        Person person = personService.addPerson(personDTO.getFirstName(), personDTO.getLastName(),
                personDTO.getCountry(), personDTO.getPosition(), personDTO.getAge(), personDTO.getBoss());
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(person.convertToDTO(), HttpStatus.CREATED);
        }
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {

        List<Person> personList = personService.getAllPersons();

        return personList.stream()
                .map(Person::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/paged")
    public List<PersonDTO> getAllPersonsPage(@RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "id") String sortBy) {

        List<Person> personList = personService.getAllPersonsByPage(pageNo, pageSize, sortBy);

        return personList.stream()
                .map(Person::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/search/{term}")
    public List<String> getItemsBySearchTerm(@PathVariable("term") String term) {
        return personService.searchAllItems(term);
    }


    @GetMapping(path = "/anns")
    public List<Person> getAllAnns() {
        Collection<Person> persons = personService.findAllAnns();
        return new ArrayList<>(persons);
    }

    @GetMapping(path = "{id}")
    public PersonDTO getPersonById(@PathVariable("id") Long id) {
        Person person = personService.getPersonById(id)
                .orElseThrow(() -> new NoSuchElementException("item " + id + " does not exits."));
        return person.convertToDTO();
    }

    @DeleteMapping(path = "{id}")
    public int deletePersonById(@PathVariable("id") Long id) {
        return personService.deletePersonById(id);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<PersonDTO> updateItemById(@PathVariable("id") Long id, @RequestBody PersonDTO personDTO) {
        if (!Objects.equals(id, personDTO.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }

        try {
            Person person = personDTO.convertToEntity(personDTO);
            Person updatedPerson = personService.updatePerson(person);
            if (updatedPerson == null) throw new NoSuchElementException("item to update NOT found.");
            return ResponseEntity.ok(updatedPerson.convertToDTO());
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("catch: " + e.getMessage());
            throw new NoSuchElementException("Item to update Exception.");
        }
    }

    @PutMapping(path = "/p/{pid}/c/{cid}")
    public PersonDTO addPersonToCountry(@PathVariable("pid") Long pid, @PathVariable("cid") Long cid) {
        Person person = personService.getPersonById(pid)
                .orElseThrow(() -> new NoSuchElementException("Person id does not exist."));
        Country country = countryService.getCountryById(cid)
                .orElseThrow(() -> new NoSuchElementException("item to update NOT found."));
        return personService.addPersonToCountry(person, country).convertToDTO();
    }

    @GetMapping(path = "bycountry/{cid}")
    public List<PersonDTO> getPersonsByCountry(@PathVariable("cid") Long cid) {
        Collection<Person> persons = personService.findPersonsWithPassportsByCountry(cid);
        Collection<PersonDTO> personsDTO;

        personsDTO = persons.stream()
                .map(Person::convertToDTO)
                .collect(Collectors.toList());

        return new ArrayList<>(personsDTO);
    }

    @GetMapping(params = {"page", "size"})
    public List<Person> findPaginated(@RequestParam("page") int page,
                                      @RequestParam("size") int size,
                                      UriComponentsBuilder uriBuilder,
                                      HttpServletResponse response) {
        return personService.findPaginated(page, size);
    }

    @GetMapping("/massupdate")
    public List<Person> massUpdate() {
        return personService.massUpdate();
    }

    @GetMapping("/findlast")
    public long findLastId() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String msg = PersonController.class.getName() + ":" + methodName;
        String ip = GetIpAddressUtils.getIpAddress(this.request);
        log.info(msg + " - findlast: request from address: " + ip);

        return personService.findLastId();
    }
    @GetMapping(value = "/ping")
    public  ResponseEntity<String> testErrorResponse() throws SQLException {
        throw new SQLException("Sql Exception");
    }

    @PostMapping(value = "/logger")
    public Boolean logger() {
        return true;
    }

    @GetMapping(value = "/ping/{pathVar}")
    public PongMessage ping(@PathVariable Long pathVar,
                            @RequestParam(value = "param", defaultValue = "defaultParam") String param) {
        return new PongMessage(String.format("pong: %d, %s", pathVar, param));
    }

    @PostMapping(value = "/account")
    public SignupResponse postCreateAccount(HttpServletResponse response,
                                      @RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "useremail") String useremail,
                                      @RequestParam(value = "phone") String phone) {
        try {

            if(!userService.checkUserExists(username)) {
                User newUser = User.builder()
                        .username(username)
                        .password(password)
                        .useremail(useremail)
                        .phone(phone)
                        .active(true)
                        .roles("ROLE_USER")
                        .build();
                newUser.setPassword(newUser.getEncryptPassword(password));
                userService.saveUser(newUser);
                return new SignupResponse("Created user " + username, true);
            }
            return new SignupResponse("Returning user " + username, false);

        } catch (Exception e) {
            log.error("Error signing up user: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new SignupResponse("An error occurred while creating your account.", false);
        }
    }

    @PostMapping(path = "/insert_order", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> insertOrder(String message) {
        orderService.addOrder(message);
        return ResponseEntity.ok("accepted order message");

    }
    public class SignupResponse {
        private String message;
        private boolean success;

        public SignupResponse(String message, boolean success) {
            super();
            this.message = message;
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    public class PongMessage {
        private String message;

        public PongMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}