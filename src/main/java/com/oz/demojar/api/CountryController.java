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
       Optional<Country> countryOpt =countryService.getCountryById(id);
       if(countryOpt.isPresent()) {
           return ResponseEntity.ok().body(countryOpt.get());
       } else {
           throw new ObjectNotFoundException("Unable to locate country with id: " + id.toString());
       }
    }

    @GetMapping(path = "/test")
    public String testCountries() {
        String str = "the number of Number Random that fits is a fits at random random order";
        String str1 = str.toLowerCase(Locale.ROOT);
        String[] words = str1.split(" ");

        HashMap<String,Integer> map = new HashMap<>();
        ArrayList<String> inWords = new ArrayList<>();
        for(int i = words.length - 1; i >= 0; i--) {
            if(map.containsKey(words[i])) {
                int count = map.get(words[i]);
                map.put(words[i], count + 1);
            } else {
                map.put(words[i], 1);
                inWords.add(words[i]);
            }
        }

        str = "sssssakkkettreeere";
        String r = "";
        int count = 0;
        try {
            char[] chars = str.toCharArray();
            HashMap<Character, Integer> resultMap = null;
            for (int i = 0; i < str.length(); i++) {
                count = 0;
                for (int j = 1; j < str.length(); j++) {
                    if (chars[i] == chars[j]) {
                        System.out.println();
                        resultMap.put(chars[i], ++count);
                    }
                }
                r = resultMap.toString();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return r;
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
