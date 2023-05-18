package com.nhnacademy.resident_project.domain;

import java.time.LocalDateTime;

public interface ResidentBirth {
    int getResidentSerialNumber();
    String getName();
    String getResidentRegistrationNumber();
    String getGenderCode();
    LocalDateTime getBirthDate();
    String getBirthPlaceCode();
    String getRegistrationBaseAddress();
}
