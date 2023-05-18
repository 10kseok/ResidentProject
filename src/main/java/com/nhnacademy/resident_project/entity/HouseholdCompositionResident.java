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
    private PK pk;
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
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PK implements Serializable {
        private int householdSerialNumber;
        private int residentSerialNumber;
    }

}
