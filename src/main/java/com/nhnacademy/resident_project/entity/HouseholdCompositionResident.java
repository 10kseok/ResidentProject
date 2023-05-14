package com.nhnacademy.resident_project.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "household_composition_resident")
public class HouseholdCompositionResident {
    @EmbeddedId
    private Pk pk;
    @MapsId("householdSerialNumber")
    @ManyToOne
    @JoinColumn(name = "household_serial_number")
    private Household household;
    @MapsId("residentSerialNumber")
    @ManyToOne
    @JoinColumn(name = "resident_serial_number")
    private Resident resident;
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;
    @Column(name = "household_relationship_code", nullable = false)
    private String householdRelationshipCode;
    @Column(name = "household_composition_change_reason_code", nullable = false)
    private String householdCompositionChangeReasonCode;

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @RequiredArgsConstructor
    public class Pk implements Serializable {
        @Column(name = "household_serial_number", nullable = false)
        private int householdSerialNumber;
        @Column(name = "resident_serial_number", nullable = false)
        private int residentSerialNumber;
    }

}
