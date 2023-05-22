package com.nhnacademy.resident_project.domain.request;

import com.nhnacademy.resident_project.entity.Household;
import com.nhnacademy.resident_project.entity.Resident;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class HouseholdRequest implements Serializable {
    private int householdSerialNumber;
    @NotNull
    private int residentSerialNumber;
    @NotNull
    private LocalDate compositionDate;
    @NotNull
    private String compositionReasonCode;
    @NotNull
    private String currentHouseMovementAddress;

    public Household toEntity(Resident resident) {
        Household household = new Household();
        household.setHouseholdSerialNumber(getHouseholdSerialNumber());
        household.setResident(resident);
        household.setHouseholdCompositionDate(getCompositionDate());
        household.setHouseholdCompositionReasonCode(getCompositionReasonCode());
        household.setCurrentHouseMovementAddress(getCurrentHouseMovementAddress());
        return household;
    }
}
