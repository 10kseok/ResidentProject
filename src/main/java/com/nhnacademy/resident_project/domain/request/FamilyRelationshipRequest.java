package com.nhnacademy.resident_project.domain;

import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyRelationshipRequest {
    @NonNull
    private int familyResidentSerialNumber;
    @NonNull
    private int baseResidentSerialNumber;
    @NonNull
    @Pattern(regexp = "[a-z]+")
    private String familyRelationshipCode;

}
