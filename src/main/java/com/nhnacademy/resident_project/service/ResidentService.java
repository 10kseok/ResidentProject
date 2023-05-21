package com.nhnacademy.resident_project.service;

import com.nhnacademy.resident_project.domain.request.BirthDeathReportRequest;
import com.nhnacademy.resident_project.domain.request.CertificateIssueRequest;
import com.nhnacademy.resident_project.domain.request.FamilyRelationshipRequest;
import com.nhnacademy.resident_project.domain.request.ResidentRequest;
import com.nhnacademy.resident_project.entity.FamilyRelationship;
import com.nhnacademy.resident_project.entity.Resident;
import com.nhnacademy.resident_project.exception.DuplicateSerialNumberException;
import com.nhnacademy.resident_project.exception.InvalidTypeCodeException;
import com.nhnacademy.resident_project.exception.ResidentNotFoundException;
import com.nhnacademy.resident_project.repository.FamilyRelationshipRepository;
import com.nhnacademy.resident_project.repository.ResidentRepository;
import com.nhnacademy.resident_project.util.RandomLongGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResidentService {
    private final ResidentRepository residentRepository;
    private final FamilyRelationshipRepository familyRelationshipRepository;

    // 주민
    public int save(ResidentRequest residentRequest) {
        if (residentRepository.existsById(residentRequest.getResidentSerialNumber())) {
            throw new DuplicateSerialNumberException();
        }

        Resident resident = residentRequest.toEntity();

        residentRepository.saveAndFlush(resident);
        return resident.getResidentSerialNumber();
    }

    public int update(ResidentRequest residentRequest) {
        residentRepository.saveAndFlush(residentRequest.toEntity());
        return residentRequest.getResidentSerialNumber();
    }

    public Resident findResidentById(Integer serialNumber) {
        Optional<Resident> resident = residentRepository.findById(serialNumber);
        if (resident.isEmpty()) {
            throw new ResidentNotFoundException();
        }
        return resident.get();
    }

    // 가족관계
    @Transactional
    public boolean save(FamilyRelationshipRequest request) {
        Optional<Resident> base = residentRepository.findById(request.getBaseResidentSerialNumber());
        Optional<Resident> family = residentRepository.findById(request.getFamilyResidentSerialNumber());

        if (base.isEmpty() || family.isEmpty()) {
            throw new ResidentNotFoundException();
        }

        FamilyRelationship entity = request.toEntity();
        entity.setBaseResident(base.get());
        entity.setFamilyResident(family.get());

        familyRelationshipRepository.saveAndFlush(entity);
        return true;
    }

    public boolean delete(FamilyRelationshipRequest request) {
        try {
            FamilyRelationship entity = request.toEntity();
            familyRelationshipRepository.deleteById(entity.getPk());
        } catch (RuntimeException ignore) {
            return false;
        }
        return true;
    }

    // 출생 신고
    @Transactional
    public boolean save(BirthDeathReportRequest request) {
        // validation
        if (!request.getTypeCode().equals("출생") && !request.getTypeCode().equals("사망")) {
            throw new InvalidTypeCodeException(request.getTypeCode());
        }

        if (!residentRepository.existsById(request.getReportResidentSerialNumber())) {
            throw new ResidentNotFoundException(request.getReportResidentSerialNumber());
        }
        // save
        // TODO: 출생신고서 생성
        residentRepository.insertBirthDeathReport(request.getTypeCode(),
                request.getResidentSerialNumber(),
                request.getReportResidentSerialNumber(),
                request.getBirthDeathReportDate(),
                request.getBirthReportQualificationsCode(),
                request.getDeathReportQualificationsCode(),
                request.getEmailAddress(),
                request.getPhoneNumber());

        // 증명서 이력 생성
        residentRepository.insertCertificate(RandomLongGenerator.nextLong(16),
                request.getResidentSerialNumber(),
                request.getTypeCode(),
                LocalDate.now()
        );
        return true;
    }

    @Transactional
    public boolean update(CertificateIssueRequest request) {
        // validation


        // update
        // TODO : 출생신고서 생성

        return true;
    }

    public boolean delete(CertificateIssueRequest request) {
        // validation


        // delete
        // TODO : 출생신고서만 삭제 (증명서 발급이력 삭제 x)
//        residentRepository.deleteCertificate(request.getId(),
//                request.getResidentNumber(),
//                request.getTypeCode(),
//                request.getIssueDate()
//        );
        return true;
    }
}
