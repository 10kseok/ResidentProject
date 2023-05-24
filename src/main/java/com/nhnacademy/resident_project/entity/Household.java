package com.nhnacademy.resident_project.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "household")
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "household_serial_number", nullable = false)
    private int householdSerialNumber;
    @ManyToOne
    @JoinColumn(name = "household_resident_serial_number", nullable = false)
    private Resident resident;
    @Column(name = "household_composition_date", nullable = false)
    private LocalDate householdCompositionDate;
    @Column(name = "household_composition_reason_code", nullable = false)
    private String householdCompositionReasonCode;
    @Column(name = "current_house_movement_address", nullable = false)
    private String currentHouseMovementAddress;

    @OneToMany(mappedBy = "household")
    List<HouseholdMovementAddress> addressList;
}

