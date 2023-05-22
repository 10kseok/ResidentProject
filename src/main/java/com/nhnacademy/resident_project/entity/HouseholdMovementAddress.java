package com.nhnacademy.resident_project.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "household_movement_address")
public class HouseholdMovementAddress {
    @EmbeddedId
    private PK pk;
    @MapsId("householdSerialNumber")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "household_serial_number")
    private Household household;
    @Column(name = "house_movement_address", nullable = false)
    private String houseMovementAddress;
    @Column(name = "last_address_yn", nullable = false)
    private char isLastAddress;

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PK implements Serializable {
        @Column(name = "house_movement_report_date", nullable = false)
        private LocalDate houseMovementReportDate;

        private int householdSerialNumber;
    }
}
