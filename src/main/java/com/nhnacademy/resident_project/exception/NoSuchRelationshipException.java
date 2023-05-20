package com.nhnacademy.resident_project.exception;

public class NoSuchRelationException extends RuntimeException {
    public NoSuchRelationException() {
        super("유효하지 않은 관계입니다.");
    }
}
