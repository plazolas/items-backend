package com.oz.demojar.service;

import com.oz.demojar.config.WebClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MusicBrainzService {
    @Value("${com.oz.demojar.service.musicbrainz}")
    private String musicBrainz = "https://musicbrainz.org/ws/2";

    @Autowired
    private WebClientConfiguration webClientConfiguration;

    @Cacheable(value = "artist", key = "#sid")
    public String getArtistById(String sid) {
        String serviceURL = musicBrainz + "/artist/" + sid + "?fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers)
                .block();
    }
    @Cacheable(value = "artists", key = "#artist")
    public String getArtistsByName(String artist) {
        log.info("Added the artists cache: " + artist);
        String serviceURL = musicBrainz + "/artist?query=" + artist + "&limit=100&offset=0&fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers)
                .block();
    }

    public String getReleasesByArtistSid(String sid) {
        String serviceURL = musicBrainz + "/artist/" + sid + "?inc=releases" + "&limit=100&offset=0&fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers)
                .block();
    }

    public String getMediaByReleaseSid(String sid) {
        String serviceURL = musicBrainz + "/release/" + sid + "?inc=recordings" + "&limit=100&offset=0&fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers)
                .block();
    }


}
