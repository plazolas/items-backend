package com.oz.demojar.api;

import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.PassportService;
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
        System.out.println(p);
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
