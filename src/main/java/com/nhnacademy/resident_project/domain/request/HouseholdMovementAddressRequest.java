package com.nhnacademy.resident_project.domain.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class HouseholdMovementAddressRequest implements Serializable {
    private LocalDate reportDate;
    private int householdSerialNumber;
    @NotNull
    private String houseMovementAddress;
    @NotNull
    private char lastAddressYN;
}
