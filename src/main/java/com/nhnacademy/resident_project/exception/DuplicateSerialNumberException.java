package com.nhnacademy.resident_project.exception;

public class DuplicateSerialNumberException extends RuntimeException {
    public DuplicateSerialNumberException() {
        super("이미 등록된 일련번호 입니다.");
    }
}
