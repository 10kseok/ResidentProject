package com.nhnacademy.resident_project.exception;

public class NoSuchRelationshipException extends RuntimeException {
    public NoSuchRelationshipException() {
        super("유효하지 않은 관계입니다.");
    }
}
