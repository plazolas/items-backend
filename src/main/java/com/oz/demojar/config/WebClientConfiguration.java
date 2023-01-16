package com.oz.demojar.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

@Configuration
@Slf4j
public class WebClientConfiguration extends ResponseEntityExceptionHandler {
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

    public Mono<String> post(String path, HttpHeaders headers, String body) {
        return webClient()
                .post()
                .uri(path)
                .headers(buildHeaders(headers))
                .body(BodyInserters.fromValue(body))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(String.class);
                    } else if (response.statusCode().is5xxServerError()) {
                        return Mono.just("Server Error");
                    } else if (response.statusCode().is4xxClientError()) {
                        return Mono.just("Not found");
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                });
    }

    public Mono<String> get(String path, HttpHeaders headers) {
        return  webClient()
                .get()
                .uri(path)
                .headers(buildHeaders(headers))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(String.class);
                    } else if (response.statusCode().is5xxServerError()) {
                        return Mono.just("Server Error");
                    } else if (response.statusCode().is4xxClientError()) {
                        return Mono.just("Not found");
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                });
    }

    private Consumer<HttpHeaders> buildHeaders(HttpHeaders httpHeaders) {
        return headers -> {
            headers = httpHeaders;
        };
    }
}
