package com.nhnacademy.resident_project.domain.request;

import com.nhnacademy.resident_project.domain.reponse.ReportSuccessDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthDeathReportRequest implements Serializable, ReportSuccessDTO {
    @NotNull
    private String typeCode;
    private int residentSerialNumber;
    private int reportResidentSerialNumber;
    @NotNull
    private LocalDate birthDeathReportDate;
    private String birthReportQualificationsCode;
    private String deathReportQualificationsCode;
    private String emailAddress;
    @NotNull
    private String phoneNumber;
}
