package com.nhnacademy.resident_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "resident")
public class Resident {
    @Id
    @Column(name = "resident_serial_number")
    private int residentSerialNumber;
    private String name;
    @Column(name = "resident_registration_number", nullable = false)
    private String residentRegistrationNumber;
    @Column(name = "gender_code", nullable = false)
    private String genderCode;
    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;
    @Column(name = "birth_place_code", nullable = false)
    private String birthPlaceCode;
    @Column(name = "registration_base_address", nullable = false)
    private String registrationBaseAddress;
    @Column(name = "death_date")
    private LocalDateTime deathDate;
    @Column(name = "death_place_code")
    private String deathPlaceCode;
    @Column(name = "death_place_address")
    private String deathPlaceAddress;
}
