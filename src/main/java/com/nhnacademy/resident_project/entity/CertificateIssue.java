package com.nhnacademy.resident_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "certificate_issue")
public class CertificateIssue {
    @Id
    @Column(name = "certificate_confirmation_number", nullable = false)
    private Long certificateConfirmationNumber;
    @ManyToOne
    @JoinColumn(name = "resident_serial_number", nullable = false)
    private Resident issuedResident;
    @Column(name = "certificate_type_code", nullable = false)
    private String certificateTypeCode;
    @Column(name = "certificate_issue_date", nullable = false)
    private LocalDate certificateIssueDate;

    @Override
    public String toString() {
        return "CertificateIssue{" +
                "certificateConfirmationNumber=" + certificateConfirmationNumber +
                ", issuedResident=" + issuedResident +
                ", certificateTypeCode='" + certificateTypeCode + '\'' +
                ", certificateIssueDate=" + certificateIssueDate +
                '}';
    }
}
