package com.nhnacademy.resident_project.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "birth_death_report_resident")
@NoArgsConstructor
public class BirthDeathReportResident {
    @EmbeddedId
    private PK pk;

    @ManyToOne
    @MapsId("reportResidentSerialNumber")
    @JoinColumn(name = "report_resident_serial_number", nullable = false)
    private Resident reportResident;

    @Column(name = "birth_death_report_date", nullable = false)
    private LocalDate birthDeathReportDate;
    @Column(name = "birth_report_qualifications_code")
    private String birthReportQualificationsCode;
    @Column(name = "death_report_qualifications_code")
    private String deathReportQualificationsCode;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PK implements Serializable {
        @Column(name = "birth_death_type_code", nullable = false)
        private String birthDeathTypeCode;
        @Column(name = "resident_serial_number", nullable = false)
        private int residentSerialNumber;
        private int reportResidentSerialNumber;
    }
}
