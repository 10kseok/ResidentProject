package com.nhnacademy.resident_project.domain.reponse;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@NoArgsConstructor
public class ReportSuccessDTOImpl implements ReportSuccessDTO, Serializable {
    private String typeCode;
    private int residentSerialNumber;
    private int reportResidentSerialNumber;
    @Override
    public String getTypeCode() {
        return typeCode;
    }

    @Override
    public int getResidentSerialNumber() {
        return residentSerialNumber;
    }

    @Override
    public int getReportResidentSerialNumber() {
        return reportResidentSerialNumber;
    }

    public ReportSuccessDTO getFrom(ReportSuccessDTO reportSuccessDTO) {
        this.typeCode = reportSuccessDTO.getTypeCode();
        this.residentSerialNumber = reportSuccessDTO.getResidentSerialNumber();
        this.reportResidentSerialNumber = reportSuccessDTO.getReportResidentSerialNumber();
        return this;
    }
}
