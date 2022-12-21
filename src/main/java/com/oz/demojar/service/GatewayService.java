package com.oz.demojar.service;

import com.oz.demojar.config.WebClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class GatewayService {

    private String musicBrainz = "https://musicbrainz.org/ws/2";

    @Autowired
    private WebClientConfiguration webClientConfiguration;

    public String getArtistById(String sid) {
        String serviceURL = musicBrainz + "/artist/" + sid + "?fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers, String.class)
                .block();
    }

    public String getArtistsByName(String artist) {
        String serviceURL = musicBrainz + "/artist?query=" + artist + "&limit=100&offset=0&fmt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");

        return webClientConfiguration.get(serviceURL, headers, String.class)
                .block();
    }


}
