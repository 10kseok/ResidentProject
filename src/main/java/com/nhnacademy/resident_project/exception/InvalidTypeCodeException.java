package com.nhnacademy.resident_project.exception;

public class InvalidTypeCodeException extends RuntimeException {
    public InvalidTypeCodeException(String message) {
        super(message + "는(은) 잘못된 유형코드입니다.");
    }
}
