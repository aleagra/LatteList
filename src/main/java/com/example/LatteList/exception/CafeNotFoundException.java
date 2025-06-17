package com.example.LatteList.exception;

public class CafeNotFoundException extends RuntimeException {
    public CafeNotFoundException(String message) {
        super(message);
    }
}
