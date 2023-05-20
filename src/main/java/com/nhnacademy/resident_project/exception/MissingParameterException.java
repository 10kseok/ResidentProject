package com.nhnacademy.resident_project.exception;

public class MissingParameterException extends RuntimeException {
    public MissingParameterException() {
        super("입력정보가 부족합니다");
    }
}
