package com.nhnacademy.resident_project.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "birth_death_report_resident")
public class BirthDeathReportResident {
    @EmbeddedId
    private Pk pk;

    @MapsId("reportResidentSerialNumber")
    @ManyToOne
    @JoinColumn(name = "report_resident_serial_number")
    private Resident reportResident;

    @Column(name = "birth_death_repost_date", nullable = false)
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
    @NoArgsConstructor
    @RequiredArgsConstructor
    public class Pk implements Serializable {
        @Column(name = "birth_death_type_code", nullable = false)
        private String birthDeathTypeCode;
        @Column(name = "resident_serial_number", nullable = false)
        private int residentSerialNumber;
        @Column(name = "report_resident_serial_number", nullable = false)
        private int reportResidentSerialNumber;
    }
}
