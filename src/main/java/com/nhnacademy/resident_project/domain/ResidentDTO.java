package com.nhnacademy.resident_project.domain;

import java.time.LocalDate;

public interface ResidentDTO {
    int getResidentSerialNumber();

    String getName();

    String getResidentRegistrationNumber();

    String getGenderCode();

    String getBirthPlaceCode();
    String getRegistrationBaseAddress();

    LocalDate getDeathDate();

    String getDeathPlaceCode();

    String getDeathPlaceAddress();
}
