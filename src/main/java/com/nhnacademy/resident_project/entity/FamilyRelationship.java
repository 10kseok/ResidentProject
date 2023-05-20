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
    @JoinColumn(name = "family_resident_serial_number", nullable = false)
    private Resident familyResident;
    @MapsId("baseResidentSerialNumber")
    @ManyToOne
    @JoinColumn(name = "base_resident_serial_number", nullable = false)
    private Resident baseResident;

    @Column(name = "family_relationship_code")
    private String familyRelationshipCode;

    @Embeddable
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PK implements Serializable {
        private int familyResidentSerialNumber;
        private int baseResidentSerialNumber;
    }
}


