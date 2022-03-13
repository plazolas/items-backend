package com.oz.demojar.api;

import com.oz.demojar.model.Country;
import com.oz.demojar.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
    public Country selectCountryById(@PathVariable("id") Long id) throws Exception {
        Country country = countryService.getCountryById(id);
        if (country == null || country.getId() == null) {
            throw new Exception("no country found for id: " + id);
        }
        return country;
    }

//    // Convert a predefined exception to an HTTP Status code
//    @ResponseStatus(value=HttpStatus.CONFLICT, reason="Data integrity violation")  // 409
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public void conflict() {
//        // Nothing to do
//    }

//    // Specify name of a specific view that will be used to display the error:
//    @ExceptionHandler({SQLException.class, DataAccessException.class})
//    public String databaseError() {
//        // Nothing to do.  Returns the logical view name of an error page, passed
//        // to the view-resolver(s) in usual way.
//        // Note that the exception is NOT available to this view (it is not added
//        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
//        // below.
//        return "SQLException databaseError";
//    }

    // Total control - set up a model and return the view name yourself. Or
    // consider subclassing ExceptionHandlerExceptionResolver (see below).
    @ExceptionHandler({ Exception.class, SQLException.class, DataAccessException.class,
                        DataIntegrityViolationException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleError(Exception ex) {

        Class<?> c = ex.getClass();
        String fullClassName = c.getName();
        String[] parts = fullClassName.split("\\.");
        String exName = (parts.length > 0) ? parts[parts.length - 1] : "";

        HttpStatus httpStatus;
        switch (exName) {
            case "InvalidDataAccessApiUsageException":
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
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
