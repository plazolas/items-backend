package com.oz.demojar.api;

import com.oz.demojar.model.Country;
import com.oz.demojar.service.CountryService;
import javassist.tools.rmi.ObjectNotFoundException;
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
       Optional<Country> countryOpt = countryService.getCountryById(id);
       if(countryOpt.isPresent()) {
           return ResponseEntity.ok().body(countryOpt.get());
       } else {
           throw new ObjectNotFoundException("Unable to locate country with id: " + id.toString());
       }
    }

    @GetMapping(path = "/test")
    public String testCountries() {
        return "country test done!";
    }

    @ExceptionHandler({ Exception.class, SQLException.class, DataAccessException.class,
                        DataIntegrityViolationException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> errorHandler(HttpServletRequest req, Exception ex) {

        System.out.println( "Request: " + req.getRequestURL() + " raised " + ex + "\n" + ex.getMessage());

        Class<?> c = ex.getClass();
        String fullClassName = c.getName();
        String[] parts = fullClassName.split("\\.");
        String exName = (parts.length > 0) ? parts[parts.length - 1] : "";

        HttpStatus httpStatus;
        switch (exName) {
            case "InvalidDataAccessApiUsageException":
            case "MethodArgumentTypeMismatchException":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case "DataIntegrityViolationException":
                httpStatus = HttpStatus.CONFLICT;
                break;
            case "SQLException":
            case "DataAccessException":
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            default:
                httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(ex.getMessage(), httpStatus);
    }

//    @ControllerAdvice
//    public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//        @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
//        protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
//            String bodyOfResponse = "This should be application specific";
//            return handleExceptionInternal(ex, bodyOfResponse,
//                    new HttpHeaders(), HttpStatus.CONFLICT, request);
//        }
//    }

}
