package com.oz.demojar.controllers;

import com.oz.demojar.service.MusicBrainzService;
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

@Slf4j
@CrossOrigin(maxAge = 3600)
@RequestMapping("api/vi/gateway")
@RestController
public class GatewayController {

    @Autowired
    private transient MusicBrainzService musicBrainzService;
    public GatewayController() {}

    @GetMapping(path = "/artist/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArtistBySid(
            @RequestParam String id
    ) {
        return musicBrainzService.getArtistById(id);
    }

    @PostMapping(value="/artist/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchArtistByName(
                @RequestParam String name
    ) {
        String response = musicBrainzService.getArtistsByName(name);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(value="/artist/releases/{sid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchReleasesByArtist(
            @PathVariable("sid") String sid
    ) {
        String response = musicBrainzService.getReleasesByArtistSid(sid);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(value="/artist/release/{sid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchMediaByRelease(
            @PathVariable("sid") String sid
    ) {
        String response = musicBrainzService.getMediaByReleaseSid(sid);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
