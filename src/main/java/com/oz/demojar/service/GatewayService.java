package com.oz.demojar.service;

import com.oz.demojar.config.WebClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
@Slf4j
@Service
public class GatewayService {

    private String musicBrainz = "https://musicbrainz.org/ws/2";

    @Autowired
    private WebClientConfiguration webClientConfiguration;

    @Cacheable(value = "artist", key = "#sid")
    public String getArtistById(String sid) {
        log.info("did not hit the artist cache");
        String serviceURL = musicBrainz + "/artist/" + sid + "?fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers, String.class)
                .block();
    }
    @Cacheable(value = "artists", key = "#artist")
    public String getArtistsByName(String artist) {
        log.info("did not hit the ARTISTS cache");
        String serviceURL = musicBrainz + "/artist?query=" + artist + "&limit=100&offset=0&fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers, String.class)
                .block();
    }


}
