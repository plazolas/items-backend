package com.oz.demojar.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ClientException extends RuntimeException {
    private final String message;
}