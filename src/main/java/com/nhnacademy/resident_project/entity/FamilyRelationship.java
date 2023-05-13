package com.nhnacademy.resident_project.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "family_relationship")
public class FamilyRelationship {
    @EmbeddedId
    private PK pk;
    @MapsId("familyResidentSerialNumber")
    @ManyToOne
    @JoinColumn(name = "resident_serial_number")
    private Resident residentSerialNumber;
    @Column(name = "family_relationship_code", nullable = false)
    private String familyRelationshipCode;

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public class PK implements Serializable {
        @Column(name = "family_resident_serial_number")
        private int familyResidentSerialNumber;
        @Column(name = "base_resident_serial_number")
        private int baseResidentSerialNumber;
    }
}
