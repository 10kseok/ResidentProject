package com.nhnacademy.resident_project.service;

import com.nhnacademy.resident_project.domain.request.FamilyRelationshipRequest;
import com.nhnacademy.resident_project.domain.request.ResidentRequest;
import com.nhnacademy.resident_project.entity.FamilyRelationship;
import com.nhnacademy.resident_project.entity.Resident;
import com.nhnacademy.resident_project.exception.DuplicateSerialNumberException;
import com.nhnacademy.resident_project.exception.ResidentNotFoundException;
import com.nhnacademy.resident_project.repository.FamilyRelationshipRepository;
import com.nhnacademy.resident_project.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
