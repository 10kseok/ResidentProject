package com.nhnacademy.resident_project.domain.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.nhnacademy.resident_project.entity.FamilyRelationship;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyRelationshipRequest {
    @NotNull
    @Min(1)
    @JsonAlias({"familyResidentSerialNumber", "familySerialNumber"})
    private int familyResidentSerialNumber;
    private int baseResidentSerialNumber;
    @NonNull @NotNull
    @Pattern(regexp = "[a-z]+")
    @JsonAlias({"familyRelationshipCode", "relationship"})
    private String familyRelationshipCode;

    public FamilyRelationship toEntity() {
        FamilyRelationship entity = new FamilyRelationship();
        entity.setPk(new FamilyRelationship.PK(familyResidentSerialNumber, baseResidentSerialNumber));
        entity.setFamilyRelationshipCode(familyRelationshipCode);
        return entity;
    }
}
