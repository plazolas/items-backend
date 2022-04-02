package com.oz.demojar.api;

import com.oz.demojar.model.Person;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.User;
import com.oz.demojar.security.ObjectEncryption;
import com.oz.demojar.security.StartupProperties;
import com.oz.demojar.service.PersonService;
import com.oz.demojar.service.CountryService;
import com.oz.demojar.service.UserService;
import com.oz.demojar.utils.GetIpAddressUtils;
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

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RequestMapping("api/vi/person")
@RestController
public class PersonController {

    private final String cors = "http://www.ozdev.net";
    private final PersonService personService;
    private final CountryService countryService;
    private final UserService userService;
    private final HttpServletRequest request;
    //private final Validator validator;

    @Autowired
    private StartupProperties startupProperties;

    @Autowired
    public PersonController(PersonService personService,
                            CountryService countryService,
                            UserService userService,
                            HttpServletRequest httpRequest) {
        this.personService = personService;
        this.countryService = countryService;
        this.userService = userService;
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
        if (savedPerson instanceof Person) {
            return new ResponseEntity<Person>(savedPerson, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Person> getAllPeople() {
        String ip = GetIpAddressUtils.getIpAddress(this.request);
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
        return personService.getPersonById(id);
    }

    @DeleteMapping(path = "{id}")
    public int deletePersonById(@PathVariable("id") Long id) {
        return personService.deletePersonById(id);
    }

    @PutMapping(path = "{id}")
    public boolean updatePersonByIdShort(@PathVariable("id") Long id, @RequestBody Person person) {
        return personService.updatePersonByIdShort(id, person);
    }

    @PutMapping(path = "/p/{id}")
    public Person updatePersonById(@PathVariable("id") Long id, @RequestBody Person person) {
        return personService.updatePersonById(id, person);
    }

    @PutMapping(path = "/p/{pid}/c/{cid}")
    @CrossOrigin(origins = cors)
    public int addPersonToCountry(@PathVariable("pid") Long pid, @PathVariable("cid") Long cid) {
        Person p = personService.getPersonById(pid);
        Country c = countryService.getCountryById(cid);
        if (p == null || c == null) {
            personService.addPersonToCountry(p, c);
            return 1;
        } else {
            return 0;
        }
    }

    @PutMapping(path = "bycountry/{cid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getPeopleByCountry(@PathVariable("cid") Long cid) {
        System.out.println("cid :" + cid);
        Collection<Person> persons = personService.findPersonsWithPassportsByCountry(cid);
        return persons.stream().collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "size"})
    public List<Person> findPaginated(@RequestParam("page") int page,
                                      @RequestParam("size") int size,
                                      UriComponentsBuilder uriBuilder,
                                      HttpServletResponse response) {
        List<Person> resultPage = personService.findPaginated(page, size);
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

    @GetMapping(value="/ping")
    public String ping() throws NoSuchPaddingException, NoSuchAlgorithmException {
//        User user = userService.getUserById(175L);
//
//        SealedObject sealedObject = null;
//        String algorithm = "AES";
//
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        SecretKey key = keyGenerator.generateKey();
//
//        // Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");     // only CBC mode supports iv
//
//        byte[] iv = new byte[128/8];
//        Random random = new Random();
//        random.nextBytes(iv);
//        IvParameterSpec ivSpec = new IvParameterSpec(iv);
//        try {
//            sealedObject = ObjectEncryption.encryptObject(algorithm, user, key, ivSpec);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        return "pong";
    }

    @GetMapping(value="/ping/{pathVar}")
    public PongMessage ping(@PathVariable Long pathVar,
                            @RequestParam(value="param", defaultValue="defaultParam") String param) {
        return new PongMessage(String.format("pong: %d, %s", pathVar, param));
    }

    @PostMapping(value="/account")
    public SignupResponse postAccount(HttpServletResponse response,
                                      @RequestParam(value="username") String username,
                                      @RequestParam(value="password") String password) {
        try {
            User newUser = User.builder()
                    .username(username)
                    .password(password)
                    .active(true)
                    .roles("ROLE_ADMIN, ROLE_USER")
                    .build();
            newUser.setPassword(newUser.getEncryptPassword(password));
            userService.saveUser(newUser);
            return new SignupResponse("Created user "+ username, true);
        } catch(Exception e) {
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
}
