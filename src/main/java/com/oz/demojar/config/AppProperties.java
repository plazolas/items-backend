package com.oz.demojar.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.properties")
public class AppProperties {
    @Value("${spring.datasource.datetime}")
    private String datetime;

    @Value("${spring.datasource.timezone}")
    private String timezone;

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;
}
