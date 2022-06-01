package com.oz.demojar.api;

import com.oz.demojar.config.AppProperties;
import com.oz.demojar.dto.PersonDTO;
import com.oz.demojar.model.Person;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.User;
import com.oz.demojar.security.StartupProperties;
import com.oz.demojar.service.PersonService;
import com.oz.demojar.service.CountryService;
import com.oz.demojar.service.UserService;
import com.oz.demojar.utils.GetIpAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
import static java.util.stream.Collectors.toMap;
import org.modelmapper.ModelMapper;


@CrossOrigin(maxAge = 3600)
@RequestMapping("api/vi/person")
@RestController
public class PersonController {

    private final String cors = "http://www.ozdev.net";

    private final HttpServletRequest request;
    //private final Validator validator;

    @Autowired
    private final AppProperties appProperties = new AppProperties();

    private StartupProperties startupProperties;

    @Autowired
    private transient CountryService countryService;
    @Autowired
    private transient UserService userService;
    @Autowired
    private transient PersonService personService;

    @Autowired
    public PersonController(HttpServletRequest httpRequest) {
        this.request = httpRequest;

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //this.validator = factory.getValidator();
    }

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
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {
        Person savedPerson = personService.addPerson(person.getFirstName(), person.getLastName(),
                person.getCountry(), person.getPosition(), person.getAge(), person.getBoss());
        if (savedPerson == null) {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Person>(savedPerson, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public List<Person> getAllPersons() {
        System.out.println("PersonController.getAllPersons: ");
        System.out.println(appProperties);
        String ip = GetIpAddressUtils.getIpAddress(this.request);
        System.out.println("request from address: " + ip);
        return personService.getAllPersons();
    }

    @GetMapping(path = "/search/{term}")
    public List<String> getItemsBySearchTerm(@PathVariable("term") String term) {
        return personService.searchAllItems(term);
    }

    @CrossOrigin(origins = cors)
    @GetMapping(path = "/anns")
    public List<Person> getAllAnns() {
        Collection<Person> persons = personService.findAllAnns();
        return new ArrayList<>(persons);
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") Long id) {
        return personService.getPersonById(id)
                .orElseThrow(() -> new NoSuchElementException("item " + id + " does not exits."));
    }

    @DeleteMapping(path = "{id}")
    public int deletePersonById(@PathVariable("id") Long id) {
        return personService.deletePersonById(id);
    }

    @PutMapping(path = "/p/{id}")
    public ResponseEntity<Person> updateItemById(@PathVariable("id") Long id, @Valid @RequestBody PersonDTO personDetails) {
        if(!Objects.equals(id, personDetails.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }

        personService.getPersonById(personDetails.getId())
                .orElseThrow(() -> new NoSuchElementException("item " + id + " does not exits."));

        try {
            Person updatedPerson = personService.updatePerson(personDetails.convertToEntity(appProperties, personDetails));
            if (updatedPerson == null) throw new NoSuchElementException("item to update NOT found.");

            return ResponseEntity.ok(updatedPerson);

        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("catch: " + e.getMessage());
            throw new NoSuchElementException("Item to update Exception.");
        }

    }

    @PutMapping(path = "/p/{pid}/c/{cid}")
    @CrossOrigin(origins = cors)
    public int addPersonToCountry(@PathVariable("pid") Long pid, @PathVariable("cid") Long cid) {
        Optional<Person> p = personService.getPersonById(pid);
        Optional<Country> c = countryService.getCountryById(cid);
        if (p.isPresent() && c.isPresent()) {
            personService.addPersonToCountry(p.get(), c.get());
            return 0;
        } else {
            return 1;
        }
    }

    @PutMapping(path = "bycountry/{cid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getPeopleByCountry(@PathVariable("cid") Long cid) {
        System.out.println("cid :" + cid);
        Collection<Person> persons = personService.findPersonsWithPassportsByCountry(cid);
        return new ArrayList<>(persons);
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
        return personService.findLastId();
    }

    @GetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping(value = "/ping/{pathVar}")
    public PongMessage ping(@PathVariable Long pathVar,
                            @RequestParam(value = "param", defaultValue = "defaultParam") String param) {
        return new PongMessage(String.format("pong: %d, %s", pathVar, param));
    }

    @PostMapping(value = "/account")
    public SignupResponse postAccount(HttpServletResponse response,
                                      @RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password) {
        try {
            User newUser = User.builder()
                    .username(username)
                    .password(password)
                    .active(true)
                    .roles("ROLE_ADMIN, ROLE_USER")
                    .build();
            newUser.setPassword(newUser.getEncryptPassword(password));
            userService.saveUser(newUser);
            return new SignupResponse("Created user " + username, true);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new SignupResponse("An error occurred while creating your account.", false);
        }
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

    @ExceptionHandler({Exception.class, SQLException.class, DataAccessException.class,
            DataIntegrityViolationException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity errorHandler(HttpServletRequest req, Exception ex) {


        Class<?> c = ex.getClass();
        String fullClassName = c.getName();
        String[] parts = fullClassName.split("\\.");
        String exName = (parts.length > 0) ? parts[parts.length - 1] : "";

        HttpStatus httpStatus;
        switch (exName) {
            case "InvalidDataAccessApiUsageException":
            case "MethodArgumentTypeMismatchException":
            case "HttpMessageNotReadableException":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case "DataIntegrityViolationException":
            case "NumberFormatException":
                httpStatus = HttpStatus.CONFLICT;
                break;
            case "SQLException":
            case "DataAccessException":
            case "JpaSystemException":
            case "ArrayIndexOutOfBoundsException":
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case "NoSuchElementException":
                httpStatus = HttpStatus.NO_CONTENT;
                break;
            default:
                httpStatus = HttpStatus.NOT_FOUND;

        }
        System.out.println("Request: " + req.getRequestURL() +
                " raised:" + ex + "\n" + ex.getMessage() + "--" + exName);
        String message = ex.getMessage() + "--" + exName;
        return new ResponseEntity(message, httpStatus);
    }


}