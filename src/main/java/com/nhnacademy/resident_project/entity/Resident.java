package com.nhnacademy.resident_project.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "resident")
@NoArgsConstructor
@AllArgsConstructor
public class Resident implements Serializable {
    @Id
    @Column(name = "resident_serial_number", nullable = false)
    private int residentSerialNumber;
    @Column(nullable = false)
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

    @OneToMany(mappedBy = "baseResident", cascade = CascadeType.REMOVE)
    private List<FamilyRelationship> baseRelationships = new ArrayList<>();
    @OneToMany(mappedBy = "familyResident", cascade = CascadeType.REMOVE)
    private List<FamilyRelationship> familyRelationships = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resident)) return false;
        Resident resident = (Resident) o;
        return residentSerialNumber == resident.residentSerialNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(residentSerialNumber);
    }
}
