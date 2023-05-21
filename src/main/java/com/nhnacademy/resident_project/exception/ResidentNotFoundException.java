package com.nhnacademy.resident_project.exception;

public class ResidentNotFoundException extends RuntimeException {
    public ResidentNotFoundException() {
        super("해당 주민일련번호는 존재하지 않습니다.");
    }

    public ResidentNotFoundException(int residentNumber) {
        super(residentNumber + " : 해당 주민일련번호는 존재하지 않습니다.");
    }
}
