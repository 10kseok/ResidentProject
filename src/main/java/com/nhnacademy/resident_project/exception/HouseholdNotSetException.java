package com.nhnacademy.resident_project.exception;

public class HouseholdNotSetException extends RuntimeException {
    public HouseholdNotSetException(int householdSerialNumber) {
        super(householdSerialNumber + ": 세대일련번호를 입력해주세요.");
    }
}
