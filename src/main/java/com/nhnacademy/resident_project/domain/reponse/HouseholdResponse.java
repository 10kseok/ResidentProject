package com.nhnacademy.resident_project.domain.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
public class HouseholdResponse implements Serializable {
    private int householdSerialNumber;
    private int residentSerialNumber;
}
