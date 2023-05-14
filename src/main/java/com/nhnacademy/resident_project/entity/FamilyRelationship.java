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
    private Pk pk;
    @MapsId("baseResidentSerialNumber")
    @ManyToOne
    @JoinColumn(name = "base_resident_serial_number")
    private Resident baseResident;
    @Column(name = "family_relationship_code", nullable = false)
    private String familyRelationshipCode;

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public class Pk implements Serializable {
        @Column(name = "family_resident_serial_number", nullable = false)
        private int familyResidentSerialNumber;
        @Column(name = "base_resident_serial_number", nullable = false)
        private int baseResidentSerialNumber;
    }
}
