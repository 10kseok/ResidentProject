package com.nhnacademy.resident_project.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateIssueRequest implements Serializable {
    private Long confirmationNumber;
    private int residentSerialNumber;
    private String typeCode;
    private LocalDate issueDate;
}
