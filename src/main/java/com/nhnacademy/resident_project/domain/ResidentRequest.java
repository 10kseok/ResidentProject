package com.nhnacademy.resident_project.domain;

import com.nhnacademy.resident_project.entity.Resident;
import lombok.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResidentRequest {
    @NonNull
    private int residentSerialNumber;
    @NonNull
    @Pattern(regexp = "[a-zA-Z가-힣]+")
    private String name;
    @NonNull
    private String residentRegistrationNumber;
    @NonNull
    @Pattern(regexp = "남|여")
    private String genderCode;
    @Past
    @NonNull
    private LocalDateTime birthDate;
    @NonNull
    private String birthPlaceCode;
    @NonNull
    private String registrationBaseAddress;
    private LocalDateTime deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;

    public Resident toResident() {
        Resident resident = new Resident();

        resident.setResidentSerialNumber(getResidentSerialNumber());
        resident.setName(getName());
        resident.setResidentRegistrationNumber(getResidentRegistrationNumber());
        resident.setGenderCode(getGenderCode());
        resident.setBirthDate(getBirthDate());
        resident.setBirthPlaceCode(getBirthPlaceCode());
        resident.setRegistrationBaseAddress(getRegistrationBaseAddress());
        resident.setDeathDate(getDeathDate());
        resident.setDeathPlaceCode(getDeathPlaceCode());
        resident.setDeathPlaceAddress(getDeathPlaceAddress());

        return resident;
    }

    public static ResidentRequest from(Resident resident) {
        ResidentRequest residentRequest = new ResidentRequest();

        residentRequest.setResidentSerialNumber(resident.getResidentSerialNumber());
        residentRequest.setName(resident.getName());
        residentRequest.setResidentRegistrationNumber(resident.getResidentRegistrationNumber());
        residentRequest.setGenderCode(resident.getGenderCode());
        residentRequest.setBirthDate(resident.getBirthDate());
        residentRequest.setBirthPlaceCode(resident.getBirthPlaceCode());
        residentRequest.setRegistrationBaseAddress(resident.getRegistrationBaseAddress());
        residentRequest.setDeathDate(resident.getDeathDate());
        residentRequest.setDeathPlaceCode(resident.getDeathPlaceCode());
        residentRequest.setDeathPlaceAddress(resident.getDeathPlaceAddress());

        return residentRequest;
    }
}