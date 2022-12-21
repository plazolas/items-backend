package com.oz.demojar.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

@Configuration
@Slf4j
public class WebClientConfiguration {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    public WebClient webClient;

    public WebClient getInstance() {
        if(webClient == null){
            webClient = WebClient.create();
        }
        return webClient;
    }

    public <T> Mono<T> post(String path, Map<String, String> body, Consumer<HttpHeaders> headers, Class<T> genericType) {
        log.info("Going to post for data to {}", path);
        WebClient.ResponseSpec responseSpec = webClient()
                .post()
                .uri(path)
                .headers(headers)
                .body(BodyInserters.fromValue(body))
                .retrieve();

        return responseSpec
                .onStatus(INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(String.class).map(IllegalStateException::new))
                .onStatus(BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(IllegalArgumentException::new))
                .onStatus(NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(NoSuchElementException::new))
                .bodyToMono(genericType);
    }

    public <T> Mono<T> post(String path, HttpHeaders headers, String body, Class<T> genericType) {
        return webClient()
                .post()
                .uri(path)
                .headers(buildHeaders(headers))
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(genericType);
    }

    public <T> Mono<T> get(String path, HttpHeaders headers, Class<T> genericType) {
        return webClient()
                .get()
                .uri(path)
                .headers(buildHeaders(headers))
                .retrieve()
                .bodyToMono(genericType);
    }

    private Consumer<HttpHeaders> buildHeaders(HttpHeaders httpHeaders) {
        return headers -> {
            headers = httpHeaders;
        };
    }
}
