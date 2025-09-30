package com.oz.demojar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oz.demojar.model.MusicBrainz.Artist;
import com.oz.demojar.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@AutoConfigureWebTestClient
public class WebClientConfigurationTest {

    @Value("${com.oz.demojar.service.musicbrainz}")
    private String musicBrainz;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WebClientConfiguration webClientConfiguration;

    private HttpHeaders headers;

    @BeforeEach
    public void init() {
        this.headers = new HttpHeaders();
        headers.set("X-FORWARDED-PROTO", "https");
        headers.set("Accept-Charset", "utf-8");
    }


    @Test
    public void uriWebClientNotFoundGetRequestTest() {
        String url = musicBrainz + "/NOTFOUND/artist?fmt=json";
        Mono<String> res = webClientConfiguration.get(url, headers);
        Assertions.assertTrue(Objects.requireNonNull(res.block()).contains("Not found")) ;
    }

    @Test
    public void getArtistRequestTest() {
        String url = musicBrainz + "/artist?query=" + "nirvana" + "&limit=100&offset=0&fmt=json";
        String nirvana = CommonUtils.readFile("nirvana.json");
        String resMsg = webClientConfiguration.get(url, headers).block();
        log.debug(resMsg);
        Assertions.assertNotNull(resMsg);
        Assertions.assertTrue(resMsg.contains("Nirvana"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            Artist artistResp = mapper.readValue(resMsg, Artist.class);
            Artist artistData = mapper.readValue(nirvana, Artist.class);
            Assertions.assertEquals(artistData, artistResp);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Test
    public void getArtistThroughWebTestClientTest() {

        String url = musicBrainz + "/artist?query=" + "nirvana" + "&limit=100&offset=0&fmt=json";

        webTestClient
                .get()
                .uri(url)
                .headers(httpHeaders -> {
                    httpHeaders.set("X-FORWARDED-PROTO", "https");
                    httpHeaders.setAccessControlAllowOrigin("*");
                })
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(res -> {
                    Assertions.assertNotNull(res.getResponseBody());
                    final String body = new String(res.getResponseBody(), UTF_8);
                    Assertions.assertTrue(body.contains("Nirvana"), "Successfully retrieved artist info for Nirvana");
                });
    }

    @Test
    public void testMockWebClientNotFound() {

        String url = musicBrainz + "/bogus?query=" + "nirvana" + "&limit=100&offset=0&fmt=json";

        webTestClient
                .get()
                .uri(url)
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.TEXT_PLAIN);
                    httpHeaders.setAccessControlAllowOrigin("*");
                })
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().consumeWith(res -> {
                    Assertions.assertNotNull(res.getResponseBody());
                    final String body = new String(res.getResponseBody(), UTF_8);
                    HttpStatus status = res.getStatus();
                    Assertions.assertFalse(body.isEmpty(), "Got some not found error message string");
                    Assertions.assertTrue(status.is4xxClientError(), "Got Not Found error message");
                });
    }
}
