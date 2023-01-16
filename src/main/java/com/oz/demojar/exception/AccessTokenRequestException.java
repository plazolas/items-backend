package com.oz.demojar.exception;

public class AccessTokenRequestException extends Exception {
    public AccessTokenRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
