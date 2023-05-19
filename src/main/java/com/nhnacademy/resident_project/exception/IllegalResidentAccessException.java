package com.nhnacademy.resident_project.exception;

public class IllegalResidentAccessException extends RuntimeException {
    public IllegalResidentAccessException() {
        super("주민일련번호가 일치하지 않습니다.");
    }
}
