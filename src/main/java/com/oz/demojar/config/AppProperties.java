package com.oz.demojar.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application-dev.properties")
public class AppProperties {
    @Value("${spring.datasource.datetime}")
    private String datetime;

    @Value("${spring.datasource.timezone}")
    private String timezone;

    @Override
    public String toString() {
        return "AppProperties [" +
                "datetime = " + datetime + "," +
                "timezone = " + timezone +
                "]";
    }
}
