package com.oz.demojar.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "admin")
public class StartupProperties {
    private String username;
    private String password;
}
