package com.oz.demojar.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.CountryService;
import com.oz.demojar.service.GatewayService;
import com.oz.demojar.service.OrderService;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
@Slf4j
@CrossOrigin(maxAge = 3600)
@RequestMapping("api/vi/gateway")
@RestController
public class GatewayController {

    @Autowired
    private transient GatewayService gatewayService;
    public GatewayController() {}

    @GetMapping(path = "/artist/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArtistBySid(
            @RequestParam String id
    ) {
        return gatewayService.getArtistById(id);
    }

    @PostMapping(value="/artist/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchArtistByName(
                @RequestParam String name
    ) {
        String response = gatewayService.getArtistsByName(name);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class, IllegalArgumentException.class, SQLException.class, DataAccessException.class,
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
            case "IllegalArgumentException":
            case "WebClientRequestException":
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
        log.error("Request: " + req.getRequestURL() +
                " raised: " + ex + " message: " + ex.getMessage());
        String message = "Exception: " + exName + " message: " + ex.getMessage();

        return new ResponseEntity(message, httpStatus);
    }

}
