package com.oz.demojar.security;

import java.util.Base64;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/controllers/vi/person/account/token";

    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
    public static final String JWT_KEY = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-controllers";
    public static final String TOKEN_AUDIENCE = "secure-app";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
